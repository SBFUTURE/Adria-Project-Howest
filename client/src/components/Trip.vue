<template>
	<div
		class="rounded-lg border bg-white text-card-foreground shadow-sm p-4"
		data-v0-t="card">
		<div class="flex flex-col space-y-1.5 p-3">
			<div class="flex justify-between items-center">
				<h3 class="text-2xl font-semibold leading-none tracking-tight mr-2">
					{{ state.name }}
				</h3>
				<div
					:class="`inline-flex items-center rounded-full border ${colorBg} px-2.5 py-0.5 w-fit text-xs font-semibold transition-colors focus:outline-none focus:ring-2 focus:ring-ring focus:ring-offset-2 border-transparent ${colorBg} text-white`">
					Status: {{ state.status }}
				</div>
			</div>
			<div class="flex justify-between items-center">
				<p class="text-sm text-muted-foreground">
					From: {{ state.source }} - To: {{ state.destination }}
				</p>
				<p class="text-sm text-muted-foreground">{{ state.date }}</p>
			</div>
		</div>
		<div class="p-4 space-y-2">
			<div class="flex justify-between items-center">
				<h4 class="font-semibold">Available Seats:</h4>
				<span>4</span>
			</div>
			<div class="flex justify-between items-center">
				<h4 class="font-semibold">Price:</h4>
				<span>A{{ price }}</span>
			</div>
			<div v-if="renderReview">
				<h4 class="font-semibold">Reviews:</h4>
				<div class="flex items-center space-x-1 mt-2">
					<template
						v-for="index in 5"
						:key="index">
						<svg
							id=""
							xmlns="http://www.w3.org/2000/svg"
							width="24"
							height="24"
							viewBox="0 0 24 24"
							fill="none"
							stroke="currentColor"
							stroke-width="2"
							stroke-linecap="round"
							stroke-linejoin="round"
							@mouseover="hoverStars(index)"
							@mouseleave="resetStars"
							@click="setRating(index)"
							:class="{
								'w-6 h-6 text-yellow-500 hover:cursor-pointer':
									index <= selectedStars,
								'w-6 h-6 text-gray-300 hover:cursor-pointer':
									index > selectedStars,
							}">
							<polygon
								points="12 2 15.09 8.26 22 9.27 17 14.14 18.18 21.02 12 17.77 5.82 21.02 7 14.14 2 9.27 8.91 8.26 12 2"></polygon>
						</svg>
					</template>
				</div>
			</div>
		</div>
		<div class="p-2 flex justify-between items-center">
			<button
				v-if="state.status !== 'FINISHED' && state.status !== 'CANCELLED'"
				@click="cancelRide"
				class="inline-flex items-center justify-center rounded-md text-xs font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 bg-red-500 text-white">
				Cancel Ride
			</button>
			<button
				v-if="
					state.status !== 'FINISHED' &&
					state.status !== 'CANCELLED' &&
					userId == state.driverId
				"
				@click="finishRide"
				class="inline-flex items-center justify-center rounded-md text-xs font-medium ring-offset-background transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:pointer-events-none disabled:opacity-50 border border-input hover:bg-accent hover:text-accent-foreground h-10 px-4 py-2 bg-blue-500 text-white">
				Finish Ride
			</button>
		</div>
	</div>
</template>

<script setup>
import { computed, ref, onMounted } from "vue";
import { updateRideStatus, reviewRide } from "../utils/Utils.js";
import { fetchReviews } from "../utils/Utils.js";
const props = defineProps([
	"rideId",
	"driverId",
	"seats",
	"name",
	"price",
	"source",
	"destination",
	"status",
	"date",
]);
const state = ref({ ...props });
const selectedStars = ref(0);
const isRatingSet = ref(false);
const userId = ref("");

const hoverStars = (stars) => {
	if (!isRatingSet.value) {
		selectedStars.value = stars;
	}
};

onMounted(async () => {
	userId.value = JSON.parse(localStorage.getItem("userId"));
	if (userId.value == state.value.driverId) {
		state.value.name = "Your Ride";
	}
	await fetchReviews(
		userId.value,
		JSON.parse(localStorage.getItem("user")),
		state.value.rideId,
		JSON.parse(localStorage.getItem("name"))
	).then((review) => {
		if (review != -1) {
			selectedStars.value = review;
			isRatingSet.value = true;
		}
	});
});

const resetStars = () => {
	if (selectedStars.value === 0 && !isRatingSet.value) {
		return;
	}
	selectedStars.value = isRatingSet.value ? selectedStars.value : 0;
};

const setRating = async (stars) => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	if (!isRatingSet.value) {
		await reviewRide(adriaId, state.value.rideId, userId.value, stars);
		selectedStars.value = stars;
		isRatingSet.value = true;
	}
};

const emit = defineEmits();

const cancelRide = async (e) => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	await updateRideStatus(adriaId, props.rideId, "CANCELLED");
	state.value.status = "CANCELLED";
};

const finishRide = async (e) => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	await updateRideStatus(adriaId, props.rideId, "FINISHED");
	state.value.status = "FINISHED";
};

const colorBg = computed(() => {
	switch (state.value.status) {
		case "CANCELLED":
			return "bg-red-500";
		case "ACTIVE":
			return "bg-green-500";
		case "FINISHED":
			return "bg-blue-500";
	}
});

const renderReview = computed(() => {
	return (
		(state.value.status == "FINISHED" || state.value.status == "CANCELLED") &&
		userId.value != state.value.driverId
	);
});
</script>
