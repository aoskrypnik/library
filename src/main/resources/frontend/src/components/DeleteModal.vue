<template>
  <v-dialog
      v-model="dialog"
      width="500"
  >

    <template v-slot:activator="{ on, attrs }">
      <v-btn text color="error" class="ml-2" v-bind="attrs"
             v-on="on">
        <v-icon left>mdi-trash-can-outline</v-icon>
        Delete
      </v-btn>
    </template>

    <v-card>
      <v-card-title class="headline grey lighten-3">
        Видалення
      </v-card-title>

      <v-card-text class="my-3">
        Ви дійсно хочете видалити книгу "{{book.title}}"?
      </v-card-text>

      <v-divider></v-divider>

      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
            text
            @click="dialog = false"
        >
          Ні, скасувати
        </v-btn>
        <v-btn
            color="error"
            text
            @click="deleteBook(book.isbn)"
        >
          Так, видалити
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
import axios from "axios";

const endpoint = 'http://localhost:9005';

export default {
  name: "DeleteModal",
  data() {
    return {
      dialog: false,
    }
  },
  props: [
    'book'
  ],
  methods: {
    deleteBook(isbn) {
      axios.delete(`${endpoint}/books/${isbn}`)
      this.dialog = false
    }
  }
}
</script>

<style scoped>

</style>