<template>
  <v-form ref="form">
    <v-text-field
        v-model="isbn"
        label="ISBN"
        :rules="[v => !!v || 'isbn is required']"
        required></v-text-field>

    <v-text-field
        v-model="title"
        label="Title"
        required></v-text-field>

    <v-autocomplete
        v-model="selectedAuthors"
        :items="authorNames"
        label="Authors"
        outlined
        dense
        chips
        small-chips
        multiple
        required></v-autocomplete>

    <v-autocomplete
        v-model="selectedGenres"
        :items="genreNames"
        label="Genres"
        outlined
        dense
        chips
        small-chips
        multiple
        required></v-autocomplete>

    <v-slider
        v-model="copiesNum"
        class="align-center"
        label="Number of copies"
        :min="1"
        :max="50"
        hide-details
    >
      <template v-slot:append>
        <v-text-field
            v-model="copiesNum"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 60px"
        ></v-text-field>
      </template>
    </v-slider>

    <v-slider
        v-model="publishYear"
        class="align-center"
        label="Publish year"
        :min="-2000"
        :max="2021"
        hide-details
    >
      <template v-slot:append>
        <v-text-field
            v-model="publishYear"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 60px"
        ></v-text-field>
      </template>
    </v-slider>

    <v-slider
        v-model="pagesNum"
        class="align-center"
        label="Number of pages"
        :min="1"
        :max="3000"
        hide-details
    >
      <template v-slot:append>
        <v-text-field
            v-model="pagesNum"
            class="mt-0 pt-0"
            hide-details
            single-line
            type="number"
            style="width: 60px"
        ></v-text-field>
      </template>
    </v-slider>

    <v-autocomplete
        v-model="publishCountry"
        :items="countries"
        label="Country of publishing"
        outlined
        dense
        chips
        small-chips
        required></v-autocomplete>

    <v-autocomplete
        v-model="language"
        :items="languages"
        label="Language"
        outlined
        dense
        chips
        small-chips
        required></v-autocomplete>

    <v-file-input
        v-model="image"
        accept=".jpg, .png"
        label="Upload image"
        filled
        prepend-icon="mdi-camera"
    ></v-file-input>

    <v-btn
        class="mr-4"
        @click="submit"
        color="primary">
      save
    </v-btn>

    <v-btn @click="clear">
      clear
    </v-btn>
  </v-form>
</template>

<script>
import axios from "axios";
import countries from "countries-list"

const endpoint = 'http://localhost:9005';

export default {
  name: "BookSaveForm",
  data: () => ({
    isbn: '',
    title: '',
    selectedAuthors: [],
    authors: [],
    authorNames: [],
    selectedGenres: [],
    genres: [],
    genreNames: [],
    copiesNum: 0,
    publishYear: 0,
    pagesNum: 0,
    countries: [],
    publishCountry: '',
    languages: [],
    language: '',
    image: null,
  }),
  created() {
    axios.get(`${endpoint}/authors`).then(response => {
      this.authors = response.data
      this.authorNames = this.authors.map(author => author.authorName)
    })
    axios.get(`${endpoint}/genres`).then(response => {
      this.genres = response.data
      this.genreNames = this.genres.map(genre => genre.genreName)
    })
    this.countries = Object.entries(countries.countries).map(item => item[1].name)
    this.languages = Object.entries(countries.languages).map(item => item[1].name)
  },
  methods: {
    submit() {
      if (this.$refs.form.validate()) {
        let book = {
          isbn: this.isbn,
          title: this.title,
          authors: this.selectedAuthors.map(authorName => {
                return {id: this.authors.find(item => item.authorName === authorName).id}
              }
          ),
          genres: this.selectedGenres.map(genreName => {
                return {id: this.genres.find(item => item.genreName === genreName).id}
              }
          ),
          copiesNum: this.copiesNum,
          publishYear: this.publishYear,
          pagesNum: this.pagesNum,
          publishCountry: this.publishCountry,
          language: this.language,
        }
        let formData = new FormData();
        formData.append('image', this.image)
        formData.append('book', JSON.stringify(book))
        axios.post(`${endpoint}/books`, formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        }).then(response => {
          console.log(response)
          this.$router.push(`/`)
        })
      }
    },
    clear() {
      this.isbn = ''
      this.title = ''
      this.selectedAuthors = []
      this.selectedGenres = []
      this.copiesNum = 0
      this.publishYear = 0
      this.pagesNum = 0
      this.publishCountry = ''
      this.language = ''
    }
  }
}
</script>
