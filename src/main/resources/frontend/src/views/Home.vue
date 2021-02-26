<template>
  <div>
    <v-container>
      <v-row>
        <v-col class="col-md-3">
          <Filters/>
        </v-col>

        <v-col class="col-md-9">
          <v-row>
            <v-col class="col-sm-12 col-md-6" v-for="book in books" :key="book.isbn">
              <Book :book="book"/>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>

  </div>
</template>

<script>

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
  data() {
    return {
      books: []
    }
  },
  created() {
    axios.get(`${endpoint}/books`).then(response => {
      this.books = response.data
      console.log(this.books)
    })
  },
}
</script>
