package app.Controlers;

import DTO.UserDto;
import app.Models.Post;
import DTO.PostDTO;
import app.Models.User;
import app.Repositories.PostRepository;
import app.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "https://super-coon-666.ru")
public class MainController {
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	@Autowired
	public MainController(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	@GetMapping("/get-posts-recommendations")
	public ResponseEntity<Map<String, Object>> getPostsRecommendations(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size,
			@RequestParam(name = "filter", required = false) String filter
	) {
		filter = filter != null ? filter.trim() : null;
		if (filter == null || filter.isEmpty()) {
			filter = "id";
		}

		Sort sort = Sort.by(Sort.Direction.ASC, filter.equals("idUserCreated") ? "user.id" : filter);
		Pageable pageable = PageRequest.of(page - 1, size, sort);

		Page<Post> postPage = postRepository.findAll(pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("posts", PostDTO.from(postPage.getContent()));
		response.put("currentPage", postPage.getNumber() + 1);
		response.put("totalItems", postPage.getTotalElements());
		response.put("totalPages", postPage.getTotalPages());

		return ResponseEntity.ok(response);
	}

	@GetMapping("/get-post-content/{id}")
	public Post getPostContent(@PathVariable String id) {
		try {
			Long postId = Long.parseLong(id);
			Post post = postRepository.findById(postId).orElse(null);
			return post;
		} catch (NumberFormatException e) {
			return null;
		}
	}

	@PostMapping("/add-post")
	public Post addPost(@RequestBody Post post) {
		return postRepository.save(post);
	}

	@GetMapping("/get-user/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setNickname(user.getNickname());
			userDto.setPswrd(user.getPswrd());

			return ResponseEntity.ok(userDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/add-user")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		Optional<User> userOpt = userRepository.findByNickname(userDto.getNickname());

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPswrd().equals(userDto.getPswrd())) {
				UserDto responseDto = new UserDto();
				responseDto.setNickname(user.getNickname());
				responseDto.setId(user.getId());

				return ResponseEntity.ok().body(responseDto);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto userDto) {
		Optional<User> existingUser = userRepository.findByNickname(userDto.getNickname());
		if (existingUser.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
		} else {
			User newUser = new User();
			newUser.setNickname(userDto.getNickname());
			newUser.setPswrd(userDto.getPswrd());
			userRepository.save(newUser);

			return ResponseEntity.ok().body("User registered successfully.");
		}
	}

	@PutMapping("/edit-post/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
		return postRepository.findById(id).map(post -> {
			post.setHeadline(postDTO.getHeadline());
			post.setContent(postDTO.getContent());
			post.setUrl(postDTO.getUrl());
			postRepository.save(post);
			return new ResponseEntity<>(PostDTO.from(post), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping("/create-post")
	public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
		Post newPost = new Post();
		newPost.setHeadline(postDTO.getHeadline());
		newPost.setContent(postDTO.getContent());
		newPost.setUrl("/content");

		User user = userRepository.findById(postDTO.getIdUserCreated())
				.orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
		newPost.setUser(user);

		postRepository.save(newPost);

		return new ResponseEntity<>(PostDTO.from(newPost), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete-post/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
		postRepository.delete(post);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/{path:[^\\.]*}", method = RequestMethod.GET)
	public String redirect() {
		return "forward:/index.html";
	}
}
