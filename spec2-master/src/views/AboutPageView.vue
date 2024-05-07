<template>
  <div class="about">
    <h1>About DataBaseWiki</h1>
    <p>
      DataBaseWiki is a web application designed to help users easily find, organize, and share information about various databases and their technologies.
    </p>
    <h2>Our Mission</h2>
    <p>
      Our mission is to make the process of learning and working with databases easier and more efficient for everyone, regardless of their experience or background.
    </p>
    <h2>Contact Us</h2>
    <p>
      If you have any questions or suggestions, please feel free to contact us at: <a href="mailto:info@database-wiki.com">info@database-wiki.com</a>
    </p>
  </div>
</template>

<script>
    export default {
        name: "UserPageView",
        data(){
            return{
                UserName: "Super Coon",
                UserAvatar: "../assets/ava.webp",
                Tg: "https://t.me/Super_Coon"
            }
        },
        computed: {
            currentTheme() {
                return this.$store.getters.currentTheme;
            },
            myElementStyle() {
                return {
                backgroundColor: this.currentTheme.backgroundColor,
                color: this.currentTheme.textColor,
                };
            },
        },
    }
</script>

<style scoped lang="scss">
.about {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  // justify-content: center;

  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;

  h1,
  h2 {
    font-weight: bold;
  }

  h1 {
    font-size: 2rem;
    margin-bottom: 1rem;
  }

  h2 {
    font-size: 1.5rem;
    margin-top: 2rem;
    margin-bottom: 1rem;
  }

  p {
    font-size: 1rem;
    line-height: 1.5;
    margin-bottom: 1rem;
  }

  a {
    color: #42b983;
  }

  @media (max-width: 768px) {
    padding: 1rem;

    h1 {
      font-size: 1.75rem;
    }

    h2 {
      font-size: 1.25rem;
    }

    p {
      font-size: 0.9rem;
    }
  }
}
</style>
