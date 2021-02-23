<template>
  <div>
    <div v-if="usernameGetter">
      <h4>You are logged in as {{ usernameGetter }}</h4>
    </div>
    <v-container>
      <v-row>
        <v-col class="col-md-3">
          <Filters/>
        </v-col>

        <v-col class="col-md-9">
              <v-row>
                <v-col class="col-sm-12 col-md-6" v-for="book in books" :key="book.isbn">
                  <Book
                      :bookIsbn=book.isbn
                      :bookTitle=book.title
                      :bookAuthors=book.authors
                      :bookGenres=book.genres
                      :bookCopies=book.copiesNum
                      :bookPages=book.pagesNum
                      :bookYear=book.publishYear
                      :bookCountry=book.publishCountry
                      :bookCover=book.imageLink
                  />
                </v-col>
              </v-row>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>

import {mapGetters} from "vuex";
import Book from "@/components/Book";
import Filters from "@/components/Filters";
import axios from "axios";
const endpoint = 'http://localhost:9005';

export default {
  name: 'Home',
  components: {
    Book,
    Filters
  },
  data () {
    return {
      books: []
    }
  },
  created() {
    axios.get(`${endpoint}/books`).then(response => {
      this.books = response.data
    })
  },
  computed: {
    ...mapGetters([
      'usernameGetter',
    ]),
  }
}
</script>
