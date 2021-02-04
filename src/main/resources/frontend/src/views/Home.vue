<template>
  <div>
    <h1>Main page</h1>
    <div v-if="usernameGetter">
      <h4>You are logged in as {{ usernameGetter }}</h4>
    </div>
  </div>
</template>

<script>

import {mapGetters} from "vuex";

export default {
  name: 'Home',

  computed: {
    ...mapGetters([
      'usernameGetter',
    ]),
  }
}
</script>
