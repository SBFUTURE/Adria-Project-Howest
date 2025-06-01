<script setup>
import { ref } from "vue";
import Navbar from "./Navbar.vue";
import Footer from "./Footer.vue";
import BecomeADriverLogin from "./BecomeADriverLogin.vue";

defineProps({
	msg: String,
});
</script>

<template>
	<Navbar />
	<become-a-driver-login />
	<Footer />
</template>

<style scoped></style>
