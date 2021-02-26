<template>
  <div>
    <div class="font-weight-medium text-h4">
      Ваше замовлення:
    </div>
    <v-container>
      <v-col class="col-md-10">
        <v-row>
          <v-col class="col-sm-12 col-md-9" v-for="book in storageGetter" :key="book.isbn">
            <BookInfoCard :book="book"/>
          </v-col>
        </v-row>
      </v-col>
    </v-container>
    <p>Дата взяття від {{ takenDateFirst }} до {{ takenDateSecond }}</p>
    <p>Очікувана дата повернення {{ estimatedReturnDate }}</p>
    <v-btn color="primary" @click="doOrder()">
      Замовити
    </v-btn>
  </div>
</template>

<script>
import axios from "axios";
import BookInfoCard from "../components/BookInfoCard";
import {mapActions, mapGetters, mapMutations} from "vuex";
import router from "@/router";

const endpoint = 'http://localhost:9005';

export default {
  name: "BookOrder",
  components: {
    BookInfoCard,
  },
  methods: {
    ...mapActions([
      'orderAction',
    ]),
    ...mapMutations([
      'storageRemoveAllMutation',
    ]),
    doOrder() {
      axios.post(`${endpoint}/orders`, {
        copiesIds: this.storageGetter.map(book => book.copies.filter(copy => copy.isAvailable)[0].id),
      }).then(response => {
        console.log(response)
        this.storageRemoveAllMutation()
        router.push('/')
      }).catch(error => {
        console.log(error)
      });
    }
  },
  data() {
    return {
      takenDateFirst: new Date().toDateString(),
      takenDateSecond: new Date(new Date().getTime() + (24 * 60 * 60 * 1000)).toDateString(),
      estimatedReturnDate: new Date(new Date().getTime() + (31 * 24 * 60 * 60 * 1000)).toDateString()
    }
  },
  computed: {
    ...mapGetters([
      'storageGetter'
    ]),
  },
}
</script>
