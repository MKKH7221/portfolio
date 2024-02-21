<template>
  <div id="app">
    <Header/>
    <router-view name="Header"></router-view>
    <router-view></router-view>
  </div>
</template>

<script>
import Header from './components/Header'
export default {
  name: 'App',
  components: {
    Header
  }
}
</script>
