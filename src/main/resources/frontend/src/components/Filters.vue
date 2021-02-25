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
// import axios from "axios";
import {mapGetters} from "vuex";

// const endpoint = 'http://localhost:9005';

export default {
  name: "Filters",
  data() {
    return {
      available_only: false,
      selectedAuthors: [],
      // authors: [],
      // authorNames: [],
      selectedGenres: [],
      // genres: [],
      // genreNames: [],
    }
  },

  computed: {
    ...mapGetters([
      'genresGetter',
      'authorsGetter'
    ]),
    genreNames: function () {
      return this.genresGetter.map(genre => genre.genreName)
    },
    authorNames: function () {
      return this.authorsGetter.map(author => author.authorName)
    }
  },


}
</script>

<style scoped>

</style>