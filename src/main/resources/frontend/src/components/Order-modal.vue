<template>
  <div class="text-center">
    <v-dialog
        v-model="dialog"
        width="500"
    >

      <template v-slot:activator="{ on, attrs }">
          <v-btn text color="primary" class="ml-auto" v-bind="attrs"
                 v-on="on">
            Замовити
          </v-btn>
      </template>

      <v-card>
        <v-card-title class="headline grey lighten-3">
          Замовлення
        </v-card-title>

        <v-card-text class="my-3">
        Створити замовлення чи додати ще книжок?
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
              color="primary"
              text
              @click="dialog = false"
          >
            Додати ще
          </v-btn>
          <v-btn
              color="primary"
              text
              @click="$router.push('/book-order')"
          >
            Створити замовлення
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
export default {
  name: "OrderModal",
  data() {
    return {
      dialog: false,
    }
  },
}
</script>

<style scoped>

</style>