<template>
  <header class="app-header">
    <nav class="nav">
      <router-link to="/">Home</router-link>
      <router-link to="/cal">Footprint</router-link>
    </nav>
  </header>
</template>

<style scoped>
.app-header {
  display: flex;
  
  padding: 10px;
  position: fixed;
  width: 100%;

  background-color: #333;
  color: white;
  padding: 10px 0;
}

.nav {
  display: flex;
  gap: 20px;
  justify-content: center;
  margin-left: 20px;
}

.nav a {
  color: white;
  text-decoration: none;
}

.router-link-active {
  font-weight: bold; 
}
</style>
