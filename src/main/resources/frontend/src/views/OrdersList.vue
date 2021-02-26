<template>
  <div>
    <v-card v-for="order in orders" :key="order.id">
      <v-card-title>Order id {{ order.id }}</v-card-title>
      <v-card-subtitle>Order status {{ order.status }}</v-card-subtitle>
      <v-list>
        <v-list-item v-for="copy in order.copies" :key="copy.id">
          Book copy id {{ copy.id }}
        </v-list-item>
      </v-list>
    </v-card>
  </div>
</template>

<script>
import axios from "axios";

const endpoint = 'http://localhost:9005';

export default {
  name: "OrdersList",
  data() {
    return {
      orders: []
    }
  },
  created() {
    axios.get(`${endpoint}/orders/current-user`).then(response => {
      this.orders = response.data
      console.log(this.orders)
    })
  }
}
</script>
