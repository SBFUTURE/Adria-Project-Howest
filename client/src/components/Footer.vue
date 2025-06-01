<template>
	<footer class="bg-grey-darken-4">
		<nav>
			<article
				v-for="section in sections"
				:key="section.title">
				<h2>{{ section.title }}</h2>
				<ul>
					<li
						v-for="item in section.items"
						:key="item.text"
						role="listitem">
						<a :href="item.link">{{ item.text }}</a>
					</li>
				</ul>
			</article>
		</nav>
		<aside>
			<a
				href=""
				aria-label="Logo"></a>
		</aside>
	</footer>
</template>

<script setup>
import { ref } from "vue";
import { router } from "../main.js";

const sections = ref([
	{
		title: "DRIVER",
		items: [
			{ text: "Become a driver", link: "/2023-2024/group-13/become-a-driver" },
			{ text: "New Driver guide", link: "/2023-2024/group-13/driver/guide" },
			{ text: "Earnings", link: "/2023-2024/group-13/driver/earnings" },
			{ text: "Safety", link: "/2023-2024/group-13/driver/safety" },
		],
	},
	{
		title: "Rider",
		items: [
			{ text: "Sign up for a ride", link: "/2023-2024/group-13/rider/signup" },
			{ text: "Cities", link: "/2023-2024/group-13/rider/cities" },
			{ text: "Help", link: "/2023-2024/group-13/rider/help" },
			{ text: "Safety", link: "/2023-2024/group-13/rider/safety" },
		],
	},
	{
		title: "YoUber",
		items: [
			{ text: "Careers", link: "/2023-2024/group-13/yoUber/careers" },
			{ text: "Business", link: "/2023-2024/group-13/yoUber/business" },
			{ text: "Developers", link: "/2023-2024/group-13/yoUber/developers" },
			{ text: "About us", link: "/2023-2024/group-13/yoUber/about" },
		],
	},
]);

const handleClick = (path) => {
	router.push(path);
};
</script>

<style scoped>
footer {
	display: flex;
	align-items: center;
	color: #e1f5fe;
	padding-top: 1rem;
	max-width: 100%;
}

nav {
	text-align: center;
	display: flex;
	justify-content: space-around;
	width: 70%;
}

nav h2 {
	font-size: 2rem;
	font-weight: bold;
	border-bottom: 0.15rem solid rgba(105, 105, 105, 0.75);
	padding: 0;
	margin: 0;
}

nav ul {
	list-style-type: none;
	padding: 0;
}

nav li {
	margin: 1rem 0;
}

nav a {
	text-decoration: none;
	color: #fff;
}

nav a:hover {
	text-decoration: underline;
}

aside {
	height: 11rem;
	width: 30%;
	margin: 0 1rem;
}

aside a {
	display: block;
	background-image: url("../assets/Images/Logo's/1-removebg-preview.png");
	background-size: contain;
	background-position: center center;
	background-repeat: no-repeat;
	width: 100%;
	height: 100%;
}
</style>
