<script setup>
import { router } from "../main.js";

const handleClick = (path) => {
	router.push(path);
};
</script>
<template>
	<div class="relative something">
		<v-parallax
			class="opacity-50 z-50 bg-black h-[110vh]"
			alt="Driver picture"
			src="https://images.unsplash.com/photo-1600320254374-ce2d293c324e?q=80&w=3540&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D">
		</v-parallax>
		<div
			class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-white text-center">
			<h1 class="text-[7rem] font-medium mb-4 b">Let's Ride</h1>
			<div class="">
				<v-btn
					class=""
					size="x-large"
					variant="outlined"
					@click="handleClick('/2023-2024/group-13/become-a-driver')"
					>Apply to drive</v-btn
				>
				<v-btn
					class="ml-4"
					size="x-large"
					variant="outlined"
					@click="handleClick('/2023-2024/group-13/get-a-ride')"
					>Get a ride</v-btn
				>
			</div>
		</div>
		<div class="custom-shape-divider-bottom-1700043046">
			<svg
				data-name="Layer 1"
				xmlns="http://www.w3.org/2000/svg"
				viewBox="0 0 1200 120"
				preserveAspectRatio="none">
				<path
					d="M321.39,56.44c58-10.79,114.16-30.13,172-41.86,82.39-16.72,168.19-17.73,250.45-.39C823.78,31,906.67,72,985.66,92.83c70.05,18.48,146.53,26.09,214.34,3V0H0V27.35A600.21,600.21,0,0,0,321.39,56.44Z"
					class="shape-fill"></path>
			</svg>
		</div>
	</div>
</template>

<style scoped>
.something {
	background: rgb(0, 0, 0);
	background: radial-gradient(
		circle,
		rgba(0, 0, 0, 1) 100%,
		rgba(0, 0, 0, 0) 100%,
		rgba(255, 255, 255, 0.4500393907563025) 100%
	);
}

.custom-shape-divider-bottom-1700043046 {
	position: absolute;
	bottom: 0;
	left: 0;
	width: 100%;
	overflow: hidden;
	line-height: 0;
	transform: rotate(180deg);
}

.custom-shape-divider-bottom-1700043046 svg {
	position: relative;
	display: block;
	width: calc(219% + 1.3px);
	height: 167px;
}

.custom-shape-divider-bottom-1700043046 .shape-fill {
	fill: #000000;
}
</style>
