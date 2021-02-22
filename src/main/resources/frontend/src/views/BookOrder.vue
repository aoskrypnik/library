<template>
  <div>
    <div class="font-weight-medium text-h4">
      Ваше замовлення:
    </div>
    <div v-if="isOrderEmpty()">
      <h4>На жаль, замовлення пусте.</h4>
    </div>
    <div v-else>
      <v-container>
        <v-col class="col-md-10">
          <v-row>
            <v-col class="col-sm-12 col-md-9" v-for="book in books" :key="book.isbn">
              <BookInfoCard
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
      </v-container>
      <v-container>
        <v-col class="col-md-6">
          <v-menu
              ref="menuTakenDate"
              v-model="menuTakenDate"
              :close-on-content-click="false"
              :nudge-right="40"
              transition="scale-transition"
              offset-y
              min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                  v-model="takenDate"
                  label="Дата видачі"
                  prepend-icon="mdi-calendar"
                  readonly
                  :rules="[dateValidateTakenField]"
                  v-bind="attrs"
                  v-on="on">
              </v-text-field>
            </template>
            <v-date-picker
                v-model="takenDate"
                :min="new Date().toISOString().substr(0, 10)"
                no-title
                scrollable
            >
              <v-spacer></v-spacer>
              <v-btn
                  text
                  color="primary"
                  @click="menuTakenDate = false"
              >
                Cancel
              </v-btn>
              <v-btn
                  text
                  color="primary"
                  @click="$refs.menuTakenDate.save(takenDate)"
              >
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
        </v-col>
      </v-container>
      <v-container>
        <v-col class="col-md-6">
          <v-menu
              ref="menuReturnDate"
              v-model="menuReturnDate"
              :close-on-content-click="false"
              transition="scale-transition"
              offset-y
              min-width="auto"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                  v-model="returnDate"
                  label="Дата повернення"
                  prepend-icon="mdi-calendar"
                  :value="returnDate"
                  readonly
                  :rules="[dateValidateReturnField]"
                  v-bind="attrs"
                  v-on="on">
              </v-text-field>
            </template>
            <v-date-picker
                v-model="returnDate"
                no-title
                scrollable
                :min="new Date().toISOString().substr(0, 10)"
            >
              <v-spacer></v-spacer>
              <v-btn
                  text
                  color="primary"
                  @click="menuReturnDate = false"
              >
                Cancel
              </v-btn>
              <v-btn
                  text
                  color="primary"
                  @click="$refs.menuReturnDate.save(returnDate)"
              >
                OK
              </v-btn>
            </v-date-picker>
          </v-menu>
          <div class="d-flex">
            <v-btn
                :disabled="!(dateValidate())"
                color="success"
                depressed
                class="mr-4"
                @click="submit"
            >
              Замовити
            </v-btn>
          </div>
        </v-col>
      </v-container>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import BookInfoCard from "../components/BookInfoCard";
import {mapActions} from "vuex";

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
    isOrderEmpty() {
      return false;
    },
    dateValidateTakenField() {
      return !!(this.dateValidate()) || 'Дата видачі не може бути пізнішою за дату повернення.';
    },
    dateValidateReturnField() {
      return !!(this.dateValidate()) || 'Дата повернення не може бути ранішою за дату видачі.';
    },
    dateValidate() {
      if (new Date(this.takenDate).getDate() > new Date(this.returnDate).getDate())
        return false;
      return true;
    },
    async submit() {
      if (this.dateValidate()) {
        console.log("Order was made");
        console.log(this.takenDate);
        console.log(this.returnDate);
        this.validCreds = await this.orderAction();
      }
    },
  },
  data() {

    return {
      books: [],
      takenDate: new Date().toISOString().substr(0, 10),
      returnDate: new Date().toISOString().substr(0, 10),
      menuTakenDate: false,
      menuReturnDate: false,
    }
  },
  created() {
    axios.get(`${endpoint}/books`).then(response => {
      this.books = [((response.data)[0])]
    })
  },
}
</script>

<style scoped>

</style>