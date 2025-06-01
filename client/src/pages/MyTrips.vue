<template>
	<div class="position-relative">
		<Navbar />
		<div class="fixed bottom-4 right-4">
			<v-btn
				v-if="user"
				@click="inputForm = true"
				class="text-xl p-4 bg-grey-darken-4"
				>Add Ride</v-btn
			>
		</div>
		<div class="myTripWrapper">
			<div class="flex flex-col items-center w-full wrapper">
				<div class="monthSelector flex justify-betwen mt-3 px-3 w-70">
					<v-btn class="month-btn">All rides</v-btn>
					<v-btn class="month-btn">Current Month</v-btn>
					<v-btn class="month-btn">Following Month</v-btn>
				</div>

				<div
					class="flex flex-row flex-wrap justify-center gap-10 w-[100%] my-10">
					<Trip
						class="min-w-[24rem]"
						v-for="(trip, index) in trips"
						:key="index"
						:rideId="trip.rideId"
						:driverId="trip.driverId"
						:seats="trip.availableSeats"
						:name="trip.driverName"
						:price="trip.pricePerSeat"
						:source="trip.source.city"
						:destination="trip.destination.city"
						:status="trip.rideStatus"
						:date="trip.date"
						@open-dialog="openDialog" />
				</div>
			</div>
		</div>
		<Footer />
		<div class="text-center">
			<v-dialog
				v-model="dialog"
				width="auto">
				<v-card>
					<v-card-text>
						Information about {{ rideDetail }}. Leave review, details and etc
						goes here.
					</v-card-text>
					<v-card-actions>
						<v-btn
							color="black"
							block
							@click="dialog = false"
							>Close</v-btn
						>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</div>
	</div>
	<v-row justify="center">
		<v-dialog
			v-model="inputForm"
			persistent
			width="1024">
			<v-card class="bg-grey-darken-4">
				<v-card-title>
					<span class="text-h5">Add a Ride</span>
				</v-card-title>
				<v-card-text>
					<v-container>
						<v-row>
							<v-col cols="6">
								<v-text-field
									v-model="source"
									label="Start Address*"
									required></v-text-field>
							</v-col>
							<v-col cols="6">
								<v-text-field
									v-model="destination"
									label="Destination Address*"
									required></v-text-field>
							</v-col>
							<v-col cols="6">
								<v-text-field
									v-model="seats"
									label="Number of seats*"
									single-line
									type="number"
									required></v-text-field>
							</v-col>
							<v-col cols="6">
								<v-text-field
									v-model="price"
									label="Price Per Seat*"
									single-line
									type="number"
									required></v-text-field>
							</v-col>
							<v-col cols-6>
								<v-text-field
									type="date"
									label="Date"
									v-model="selectedDateTime"
									:min="minDateTime" />
							</v-col>
						</v-row>
					</v-container>
					<small>*indicates required field</small>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn
						color="white"
						variant="text"
						@click="inputForm = false">
						Close
					</v-btn>
					<v-btn
						color="white"
						variant="text"
						@click="submit">
						Submit
					</v-btn>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-row>
</template>

<script>
import { ref, onMounted } from "vue";
import Navbar from "../components/Navbar.vue";
import Trip from "../components/Trip.vue";
import Footer from "../components/Footer.vue";
import { createRide, getUserRides } from "../utils/Utils";

export default {
	name: "MyTrips",
	components: {
		Navbar,
		Trip,
		Footer,
	},

	setup() {
		const user = JSON.parse(localStorage.getItem("isDriver"));
		const inputForm = ref(false);
		const source = ref("");
		const destination = ref("");
		const price = ref(null);
		const seats = ref(null);
		const selectedDateTime = ref(null);
		const minDateTime = ref(null);

		const trips = ref();

		const dialog = ref(false);

		const rideDetail = ref("Ride 1");

		const openDialog = (value) => {
			dialog.value = true;
			rideDetail.value = value;
		};

		onMounted(async () => {
			const adriaId = JSON.parse(localStorage.getItem("user"));
			trips.value = await getUserRides(adriaId);
		});

		const submit = async () => {
			const adriaId = JSON.parse(localStorage.getItem("user"));
			await createRide(
				adriaId,
				source.value,
				destination.value,
				price.value,
				seats.value,
				selectedDateTime.value
			);
			inputForm.value = false;
			window.location.reload();
		};

		return {
			trips,
			dialog,
			openDialog,
			rideDetail,
			user,
			inputForm,
			submit,
			source,
			destination,
			price,
			seats,
			selectedDateTime,
			minDateTime,
		};
	},
};
</script>

<style scoped>
.wrapper {
	flex: 1;
}
.myTripWrapper {
	background-image: url("../assets/Images/Logo's/myRidesBackground.png");
	background-attachment: fixed;
	background-position: center;
	background-size: cover;
	background-color: rgba(0, 0, 0, 0.62);
	min-height: 100vh;
	box-sizing: border-box;
}

.monthSelector {
	display: flex;
	width: 70%;
	margin-top: 1rem;
	top: 0;
}

.month-btn {
	flex-grow: 1;
	margin: 0 1rem;
}
</style>
