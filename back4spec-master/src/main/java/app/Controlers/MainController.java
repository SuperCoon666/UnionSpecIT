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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;


import java.util.*;

@RestController
public class MainController {
	private final UserRepository userRepository;
	private final PostRepository postRepository;

	public MainController(UserRepository userRepository, PostRepository postRepository) {
		this.userRepository = userRepository;
		this.postRepository = postRepository;
	}

	public static List<Map<String, String>> posts = new ArrayList<Map<String, String>>() {{
		add(new HashMap<String, String>() {{
			put("template", "What is a relational database_0?");
			put("url", "/content");
			put("id", "0");
		}});
		add(new HashMap<String, String>() {{
			put("template", "What is a relational database?_1");
			put("url", "/content");
			put("id", "1");
		}});
		add(new HashMap<String, String>() {{
			put("template", "What is a relational database?_2");
			put("url", "/content");
			put("id", "2");
		}});
		add(new HashMap<String, String>() {{
			put("template", "What is a relational database?_3");
			put("url", "/content");
			put("id", "3");
		}});
	}};
//	@CrossOrigin(origins = "http://localhost:8080")
//	@GetMapping("/get-posts-recommendations")
//	public List<Map<String, String>> ResponseEntity() {
//		return posts;
//	}

//	@CrossOrigin(origins = "http://localhost:8080")
//	@GetMapping("/get-posts-recommendations")
//	public ResponseEntity<List<PostDTO>> getPostsRecommendations() {
//		List<Post> posts = postRepository.findAll(); // Получение всех постов
//		List<PostDTO> postDTOs = PostDTO.from(posts); // Преобразование в DTO
//		return ResponseEntity.ok(postDTOs);
//	}
	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/get-posts-recommendations")
	public ResponseEntity<List<PostDTO>> getPostsRecommendations(
			@RequestParam(name = "page", defaultValue = "1") int page,
			@RequestParam(name = "size", defaultValue = "10") int size
	) {
		Pageable pageable = PageRequest.of(page-1, size);
		Page<Post> postPage = postRepository.findAll(pageable); // Получение страницы постов

		List<PostDTO> postDTOs = PostDTO.from(postPage.getContent()); // Преобразование в DTO
		return ResponseEntity.ok(postDTOs);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/get-post-content/{id}")
	public Post getPostContent(@PathVariable String id) {
		try {
			Long postId = Long.parseLong(id);
			Post post = postRepository.findById(postId).orElse(null);
			return post;
		} catch (NumberFormatException e) {
			// Handle the case where id cannot be parsed to Long
			return null;
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/add-post")
	public Post addPost(@RequestBody Post post) {
		return postRepository.save(post);
	}


	@CrossOrigin(origins = "http://localhost:8080")
	@GetMapping("/get-user/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		Optional<User> userOptional = userRepository.findById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			UserDto userDto = new UserDto();
			userDto.setId(user.getId());
			userDto.setNickname(user.getNickname());
			userDto.setPswrd(user.getPswrd());
			// You may want to exclude password or sensitive information in real-world applications

			return ResponseEntity.ok(userDto);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/add-user")
	public User addUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) {
		Optional<User> userOpt = userRepository.findByNickname(userDto.getNickname());

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			if (user.getPswrd().equals(userDto.getPswrd())) {
				// Создаем новый объект DTO для возврата, включая id и nickname, но исключая пароль
				UserDto responseDto = new UserDto();
				responseDto.setNickname(user.getNickname());
				responseDto.setId(user.getId()); // Устанавливаем id пользователя
				// Не устанавливаем пароль, чтобы он не возвращался в ответе

				// Пользователь найден и пароль совпадает, возвращаем DTO
				return ResponseEntity.ok().body(responseDto);
			} else {
				// Пароль не совпадает
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
			}
		} else {
			// Пользователь с таким nickname не найден
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
		}
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody UserDto userDto) {
		// Проверяем, существует ли уже пользователь с таким nickname
		Optional<User> existingUser = userRepository.findByNickname(userDto.getNickname());
		if (existingUser.isPresent()) {
			// Если пользователь уже существует, возвращаем ошибку
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists.");
		} else {
			// Если пользователя нет, создаем нового
			User newUser = new User();
			newUser.setNickname(userDto.getNickname());
			newUser.setPswrd(userDto.getPswrd()); // В реальном приложении пароль должен быть хеширован перед сохранением
			userRepository.save(newUser);
			// Пользователь успешно создан
			return ResponseEntity.ok().body("User registered successfully.");
		}
	}
	@CrossOrigin(origins = "http://localhost:8080")
	@PutMapping("/edit-post/{id}")
	public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
		return postRepository.findById(id).map(post -> {
			post.setHeadline(postDTO.getHeadline());
			post.setContent(postDTO.getContent());
			post.setUrl(postDTO.getUrl());
			// Обновляем связь с пользователем, если это необходимо
			// Например, если у вас есть метод для установки пользователя
			// post.setUser(someMethodToFindUser(postDTO.getIdUserCreated()));
			postRepository.save(post);
			return new ResponseEntity<>(PostDTO.from(post), HttpStatus.OK);
		}).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping("/create-post")
	public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
		Post newPost = new Post();
		newPost.setHeadline(postDTO.getHeadline());
		newPost.setContent(postDTO.getContent());
		newPost.setUrl("/content"); // Установка статичного URL

		// Установка пользователя, создавшего пост
		User user = userRepository.findById(postDTO.getIdUserCreated())
				.orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
		newPost.setUser(user);

		postRepository.save(newPost);

		return new ResponseEntity<>(PostDTO.from(newPost), HttpStatus.CREATED);
	}

	@CrossOrigin(origins = "http://localhost:8080")
	@DeleteMapping("/delete-post/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		Post post = postRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
		postRepository.delete(post);

		return new ResponseEntity<>(HttpStatus.OK);
	}

}
