<template>
  <v-card class="book-card">
    <div class="d-flex flex-no-wrap justify-space-between">
      <v-img
          src="@/assets/test-cover.jpg"
          class="book-cover"
      ></v-img>
      <div>
        <v-card-title>Назва книги</v-card-title>
        <v-card-subtitle>Автор</v-card-subtitle>
        <v-card-text>
          <v-chip-group column>
            <v-chip small outlined label>Жанр</v-chip>
            <v-chip small outlined label>Жанр</v-chip>
            <v-chip small outlined label>Жанр</v-chip>
            <v-chip small outlined label>Кількість сторінок</v-chip>
            <v-chip small outlined label>Рік видання</v-chip>
            <v-chip small outlined label>Країна</v-chip>
          </v-chip-group>
          Коротка анотація, яка описує сюжет книги.
        </v-card-text>
        <v-card-actions>
          <v-btn text color="primary" class="ml-auto">Замовити</v-btn>
        </v-card-actions>
      </div>
    </div>
  </v-card>
</template>

<script>
export default {
  name: "Book"
}
</script>

<style scoped>
  .book-card {
    overflow: hidden;
  }

  .book-cover {
    width: 150px;
  }
</style>