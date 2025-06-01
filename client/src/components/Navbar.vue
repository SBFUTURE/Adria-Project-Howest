<script setup>
import { router } from "../main.js";
import { ref, computed } from "vue";

const drawer = ref(false);

const logout = () => {
	localStorage.clear();
	window.location.reload();
};

const handleClick = (path) => {
	router.push(path);
};
</script>

<template>
	<v-layout class="fixed top-2 z-[1000]">
		<v-btn
			v-if="!drawer"
			icon="mdi-menu"
			@click="drawer = true"
			class="mt-10 ml-6 bg-grey-darken-4"></v-btn>

		<v-navigation-drawer
			:width="300"
			class="bg-black"
			v-model="drawer"
			permanent>
			<v-list>
				<v-list-item>
					<template v-slot:append>
						<v-btn
							icon="mdi-close"
							variant="text"
							@click="drawer = false"></v-btn>
					</template>
				</v-list-item>

				<v-list-item>
					<v-img
						src="https://i.ibb.co/r3HwNdK/youber-logo.png"
						contain
						class="custom-image text-white"
						@click="handleClick('/2023-2024/group-13/')"
						height="200">
					</v-img>
				</v-list-item>
				<v-divider></v-divider>
			</v-list>

			<v-list class="text-h6 font-weight-bold">
				<v-list-item
					link
					prepend-icon="mdi-car"
					class="list-item"
					@click="handleClick('/2023-2024/group-13/get-a-ride')"
					><p>Get a ride</p></v-list-item
				>
				<v-list-item
					link
					prepend-icon="mdi-steering"
					class="list-item"
					@click="handleClick('/2023-2024/group-13/become-a-driver')"
					><p>Become a driver</p></v-list-item
				>
				<v-list-item
					link
					prepend-icon="mdi-currency-usd"
					class="list-item"
					@click="handleClick('/2023-2024/group-13/trips')"
					><p>My Trips</p></v-list-item
				>

				
			</v-list>
		</v-navigation-drawer>
	</v-layout>
</template>

<style scoped>
.custom-btn-width {
	width: 80%;
	margin: 0 auto;
	margin-bottom: 1rem;
}

.list-item:hover {
	background-color: #424242;
	transition: background-color 0.3s ease-in-out;
}

.custom-image {
	max-width: 100%;
}
</style>
