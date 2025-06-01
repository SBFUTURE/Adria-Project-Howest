<script setup>
import { ref } from "vue";
import Hero from "./Hero.vue";
import Navbar from "./Navbar.vue";
import SubHero from "./SubHero.vue";
import AdriaLogin from "./AdriaLogin.vue";
import Footer from "./Footer.vue";
import SafetyFirst from "./SafetyFirst.vue";

defineProps({
	msg: String,
});

const count = ref(0);
</script>

<template>
	<Hero />
	<Navbar />
	<AdriaLogin />
	<SubHero />
	<SafetyFirst />
	<Footer />
</template>

<style scoped>
.read-the-docs {
	color: #888;
}
</style>
