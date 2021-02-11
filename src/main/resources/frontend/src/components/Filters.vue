<template>
  <v-card>
    <v-card-title>
      Фільтри
    </v-card-title>
    <v-card-text>
      <v-form>
        <v-checkbox
            v-model="available_only"
            label="Тільки в наявності"
        >
        </v-checkbox>
        <v-autocomplete
            v-model="selectedAuthors"
            :items="authorNames"
            label="Автори"
            outlined
            chips
            small-chips
            multiple
        ></v-autocomplete>

        <v-autocomplete
            v-model="selectedGenres"
            :items="genreNames"
            label="Genres"
            outlined
            chips
            small-chips
            multiple
        ></v-autocomplete>

        <v-btn text block color="primary">
          Застосувати фільтри
        </v-btn>
      </v-form>
    </v-card-text>

  </v-card>
</template>

<script>
import axios from "axios";
const endpoint = 'http://localhost:9005';

export default {
  name: "Filters",
  data () {
    return {
      available_only: false,
      selectedAuthors: [],
      authors: [],
      authorNames: [],
      selectedGenres: [],
      genres: [],
      genreNames: [],
    }
  },
  created() {
    axios.get(`${endpoint}/authors`).then(response => {
      this.authors = response.data
      this.authorNames = this.authors.map(author => author.authorName)
    })
    axios.get(`${endpoint}/genres`).then(response => {
      this.genres = response.data
      this.genreNames = this.genres.map(genre => genre.genreName)
    })
  }
}
</script>

<style scoped>

</style>