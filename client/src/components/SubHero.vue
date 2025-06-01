<script setup>
import { router } from "../main.js";

const handleClick = (path) => {
	router.push(path);
};
</script>

<template>
	<div class="container-div z-10">
		<h1 class="text-5xl font-extrabold mb-6 text-center text-white">
			Drive with YoUber
		</h1>
		<h2 class="text-xl font-semibold mb-10 text-center text-gray-300">
			Set your own hours. Earn on your terms.
		</h2>

		<div class="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-5xl mx-auto">
			<div class="section-information text-center">
				<h3 class="text-2xl font-bold mb-3 text-white">Reliable Earnings</h3>
				<p class="text-gray-300">
					Make money, keep your tips, and cash out when you want.
				</p>
			</div>
			<div class="section-information text-center">
				<h3 class="text-2xl font-bold mb-3 text-white">A Flexible Schedule</h3>
				<p class="text-gray-300">
					Be your own boss and drive whenever it works for you.
				</p>
			</div>
			<div class="section-information text-center">
				<h3 class="text-2xl font-bold mb-3 text-white">Get Paid Instantly</h3>
				<p class="text-gray-300">Cash out your earnings whenever you want.</p>
			</div>
		</div>

		<div class="flex justify-center mt-10">
			<v-btn
				class="button-apply"
				size="lg"
				@click="handleClick('/2023-2024/group-13/become-a-driver')"
				>Apply to Drive</v-btn
			>
			<v-btn
				class="button-learn-more"
				size="lg"
				>How Drivers Work</v-btn
			>
		</div>
	</div>
</template>

<style scoped>
.container-div {
	background-color: black;
	color: white;
	padding: 4rem 2rem;
	position: relative;
}

h3 {
	font-weight: bold;
	font-size: 1.6rem;
}

p {
	font-size: 1.4rem;
}

.section-information {
	background-color: black;
	border: 2px solid white;
	border-radius: 8px;
	padding: 1.5rem;
	box-shadow: 0 4px 8px rgba(255, 255, 255, 0.1);
	transition: transform 0.3s;
}

.section-information:hover {
	transform: translateY(-4px);
}

.button-apply,
.button-learn-more {
	border: 2px solid white;
	color: white;
	background-color: transparent;
	padding: 1rem 2rem;
	border-radius: 0.5rem;
	cursor: pointer;
	transition: background-color 0.3s, color 0.3s;
	margin: 0 1rem;
}

.button-apply:hover,
.button-learn-more:hover {
	background-color: white;
	color: black;
}
</style>
