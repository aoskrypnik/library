<template>
  <v-card class="book-card">
    <div class="d-flex flex-no-wrap justify-space-between">
      <v-img
          :src="`/upload/${bookCover}`"
          class="book-cover"
      ></v-img>
      <div>
        <v-card-title> {{ bookTitle }} </v-card-title>
        <v-card-subtitle
            v-for="author in bookAuthors" :key="author.id"
        > {{ author.authorName }} </v-card-subtitle>
        <v-card-text>
          <div>
            <v-chip
                small
                outlined
                label
                class="chip"
                v-for="genre in bookGenres" :key="genre.id"
            > {{ genre.genreName }} </v-chip>
            <v-chip small outlined label class="chip"> {{ bookPages }} сторінок</v-chip>
            <v-chip small outlined label class="chip"> {{ bookYear }} рік</v-chip>
            <v-chip small outlined label class="chip"> {{ bookCountry }} </v-chip>
            <v-chip small outlined label color="danger" class="chip"
                    v-if="bookCopies === 0"
            > Немає в наявності </v-chip>
            <v-chip small outlined label color="success" class="chip"
                    v-else
            >
              Є в наявності </v-chip>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn text class="ml-auto"
                 v-if="bookCopies === 0"
          >
            <v-icon left>mdi-bell-outline</v-icon>
            Повідомити</v-btn>
          <v-btn text color="primary" class="ml-auto"
                 v-else
          >
            Замовити</v-btn>
        </v-card-actions>
      </div>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "Book",
  props: [
    'bookTitle',
    'bookAuthors',
    'bookGenres',
    'bookCopies',
    'bookPages',
    'bookYear',
    'bookCountry',
    'bookCover'
  ]
}
</script>

<style scoped>
  .book-card {
    overflow: hidden;
  }

  .book-cover {
    width: 150px;
  }

  .chip {
    margin-right: 8px;
    margin-bottom: 8px;
  }

</style>