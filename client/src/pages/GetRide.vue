<template>
	<div>
		<div
			ref="map"
			style="height: 100vh"></div>

		<div
			class="z-[1000] absolute top-4 right-4 bg-grey-darken-4 p-4 rounded-lg text-white w-[28rem]">
			<form
				@submit.prevent="getRide"
				class="">
				<v-text-field
					class="cursor-pointer"
					id="pickup"
					v-model="state.source"
					:error-messages="v$.source.$errors.map((e) => e.$message)"
					label="Pickup Location"
					required
					@input="v$.source.$touch"
					@blur="v$.source.$touch"
					clearable
					@change="handleAddressChange">
					<template #append>
						<v-icon @click="sourceHandler">mdi-map-marker</v-icon>
					</template>
				</v-text-field>

				<v-text-field
					id="dropoff"
					class="cursor-pointer"
					v-model="state.destination"
					:error-messages="v$.destination.$errors.map((e) => e.$message)"
					label="Dropoff Location"
					required
					@input="v$.destination.$touch"
					@blur="v$.destination.$touch"
					@change="handleAddressChange"
					clearable>
					<template #append>
						<v-icon @click="destinationHandler">mdi-map-marker</v-icon>
					</template>
				</v-text-field>
				<v-text-field
					type="number"
					label="Amount of Seats"
					:error-messages="v$.destination.$errors.map((e) => e.$message)"
					v-model="state.seats"
					@input="v$.seats.$touch"
					@blur="v$.seats.$touch"
					:min="1"
					:max="10"
					clearable
					required></v-text-field>
				<v-text-field
					type="date"
					label="Date and Time"
					:error-messages="v$.destination.$errors.map((e) => e.$message)"
					v-model="selectedDateTime"
					:min="minDateTime"
					required />
				<v-btn
					type="submit"
					class="me-4 mt-4"
					@click="v$.$validate"
					>Get A Ride!</v-btn
				>
				<v-btn
					class="mt-4"
					@click="clear"
					>Clear</v-btn
				>
			</form>
			<v-list
				v-if="state.rides.length > 0"
				class="bg-grey-darken-4 mt-4 h-64 overflow-y-scroll">
				<v-list-item-group
					v-model="state.selectedRide"
					v-for="(ride, index) in state.rides"
					:key="index"
					class="bg-grey-darken-4">
					<v-list-item @click="selectRide(ride)">
						<div
							class="flex justify-between flex-row items-center bg-grey-darken-4">
							<div class="flex-1">
								<div class="flex-1 justify-center items-start">
									<v-icon
										size="4rem"
										icon="mdi-account-circle"
										color="white"></v-icon>
								</div>
								<div class="flex-1 justify-center">
									<h2>{{ ride.driverName }}</h2>
								</div>
							</div>
							<div class="h-[100%] mr-2">
								<h2><span class="bold">From: </span>{{ ride.source.city }}</h2>
								<h2>To: {{ ride.destination.city }}</h2>
							</div>
							<div class="flex-1">
								<h2>Price: {{ ride.pricePerSeat }}</h2>
								<h2>Seats: {{ ride.availableSeats }}</h2>
							</div>
						</div>
					</v-list-item>
				</v-list-item-group>
			</v-list>
			<h1
				v-if="state.rides.length == 0 && state.searched === true"
				class="mt-4">
				There are no rides with provided info.
			</h1>
			<v-btn
				v-if="state.selectedRide"
				@click="pay"
				class="w-full"
				>Pay {{ finalPrice }} Adria Coins</v-btn
			>
		</div>
		<div class="z-[1000] absolute bottom-16 left-4 w-max">
			<v-alert
				class="bg-grey-darken-4 max-w-[50%]"
				closable
				title="How to choose location on the map"
				text="To choose the location on the map, first click on the marker next to the address input field and then click on the map. Wait a few seconds for processing and voila!"></v-alert>
		</div>
	</div>
	<div class="absolute z-[1000] top-5 left-5">
		<v-btn
			class="bg-grey-darken-4"
			icon="mdi-keyboard-return"
			@click="back"></v-btn>
	</div>
	<template>
		<div class="text-center">
			<v-dialog
				v-model="state.dialog"
				width="auto">
				<v-card class="bg-grey-darken-4">
					<v-card-text>
						You have successfully added the ride. Go to My Trips to consult it!
					</v-card-text>
					<v-card-actions>
						<v-btn
							color="white"
							block
							@click="finish"
							>My Trips</v-btn
						>
					</v-card-actions>
				</v-card>
			</v-dialog>
		</div>
	</template>
</template>

<script setup>
import { ref, reactive, onMounted, computed, watch } from "vue";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import { useVuelidate } from "@vuelidate/core";
import { router } from "../main.js";
import { required } from "@vuelidate/validators";
import { toAddress, toLatLng, planRide, fetchRides } from "../utils/Utils";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css"; // Import the CSS file
import "leaflet-routing-machine/dist/leaflet-routing-machine"; // Import the library

let map = ref(null);
let leafletmap = null;
const selectedDateTime = ref(null);
const minDateTime = ref(null);
let timeoutId;

const finalPrice = computed(() => {
	return state.selectedRide.pricePerSeat * state.seats;
});

const initialState = {
	sourceLatLng: "",
	source: "",
	destinationLatLng: "",
	destination: "",
	seats: null,
	select: null,
	checkbox: null,
	listenSource: false,
	listenDestination: false,
	markers: [],
	rides: [],
	selectedRide: null,
	dialog: false,
	searched: false,
	sourceCity: "",
	destinationCity: "",
};

const state = reactive({
	...initialState,
});

const rules = {
	source: { required },
	destination: { required },
	select: { required },
	seats: { required, Number },
};

const v$ = useVuelidate(rules, state);

const selectRide = (ride) => {
	state.selectedRide = ride;
};

const finish = () => {
	router.push("/2023-2024/group-13/trips");
};

function clear() {
	v$.value.$reset();
	for (const [key, value] of Object.entries(initialState)) {
		state[key] = value;
	}
	clearMarkers();
}

const handleAddressChange = (e) => {
	// Clear the previous timeout
	clearTimeout(timeoutId);

	// Set a new timeout
	timeoutId = setTimeout(async () => {
		await toLatLng(e.target.value).then((data) => {
			const result = data.locations[0].referencePosition;
			const city = data.locations[0].address.city;
			if (e.target.id == "pickup") {
				state.sourceLatLng = [result.latitude, result.longitude];
				state.sourceCity = city;
			} else {
				state.destinationLatLng = [result.latitude, result.longitude];
				state.destinationCity = city;
			}
			state.markers.push(addMarker(result.latitude, result.longitude));
		});
	}, 500);
};

const addMarker = (lat, lng) => {
	const LatLngArray = [lat, lng];

	const markerOptions = {
		icon: L.icon({
			iconUrl: "https://i.ibb.co/FwGyQCr/marker.png",
			iconSize: [32, 32],
		}),
	};
	const marker = L.marker(LatLngArray, markerOptions).addTo(leafletmap);

	return marker;
};

const handleMapClick = async (e) => {
	const result = await toAddress(e.latlng);
	const city = result.address.city;
	if (state.listenSource) {
		state.source = result.formattedAddress;
		state.sourceCity = city;
		state.listenSource = false;
		state.sourceLatLng = [e.latlng.lat, e.latlng.lng];
		state.markers.push(addMarker(e.latlng.lat, e.latlng.lng));
	} else if (state.listenDestination) {
		state.destination = result.formattedAddress;
		state.destinationCity = city;
		state.listenDestination = false;
		state.destinationLatLng = [e.latlng.lat, e.latlng.lng];
		state.markers.push(addMarker(e.latlng.lat, e.latlng.lng));
	}
};

const clearMarkers = () => {
	// Remove each marker from the map
	state.markers.forEach((marker) => marker.remove());

	// Clear the markers array
	state.markers = [];
};

const getRide = async () => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	state.rides = await fetchRides(
		adriaId,
		state.sourceCity,
		state.destinationCity,
		state.seats,
		selectedDateTime
	);
	if (state.rides.length == 0) {
		state.searched = true;
	}
};

const pay = async () => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	const plannedRide = await planRide(
		adriaId,
		state.selectedRide.source,
		state.selectedRide.destination,
		selectedDateTime,
		state.seats,
		state.selectedRide.driverId
	);
	if (plannedRide.failure != 400) {
		state.dialog = true;
	}
};

onMounted(() => {
	// Set the boundaries for the map
	const belgiumBounds = L.latLngBounds([50.6616, 2.5246], [51.505, 6.1566]);
	const brugesCoordinates = [51.2093, 3.2247];
	const now = new Date();
	now.setHours(now.getHours() + 1);
	minDateTime.value = now.toISOString().slice(0, -8); // Removing the timezone part

	// Initialize the map
	leafletmap = L.map(map.value, {
		zoomControl: false,
		minZoom: 8, // Set the minimum zoom level for Belgium
		maxZoom: 18, // Set the maximum zoom level for Belgium		\
		maxBounds: belgiumBounds,
		maxBoundsViscosity: 1.0, // Adjust this value based on your preference
	}).setView(brugesCoordinates, 12);

	L.tileLayer(
		"https://tiles.stadiamaps.com/tiles/alidade_smooth/{z}/{x}/{y}{r}.{ext}",
		{
			attribution:
				'&copy; <a href="https://www.stadiamaps.com/" target="_blank">Stadia Maps</a> &copy; <a href="https://openmaptiles.org/" target="_blank">OpenMapTiles</a> &copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
			ext: "png",
		}
	).addTo(leafletmap);

	// Add zoom control to top right corner
	L.control
		.zoom({
			position: "topright",
		})
		.addTo(leafletmap);

	// Set custom cursor style for the map
	leafletmap.getContainer().style.cursor = "pointer";

	leafletmap.on("click", handleMapClick);

	const control = L.Routing.control({
		waypoints: [],
		routeWhileDragging: true,
		waypoints: [],
		lineOptions: {
			styles: [{ color: "black", opacity: 1, weight: 4 }],
		},
	});

	watch(
		[() => state.sourceLatLng, () => state.destinationLatLng],
		([newSource, newDestination]) => {
			if (newDestination && newSource) {
				control.setWaypoints([L.latLng(newSource), L.latLng(newDestination)]);
				control.addTo(leafletmap);
				leafletmap.removeLayer(control.getPlan());
				leafletmap.removeLayer(control.getWaypoints());
			} else if (!newDestination && !newSource) {
				control.spliceWaypoints(0, control.getWaypoints().length, L.latLng([]));
			}
			// Add any other actions or logic you want to perform
		}
	);

	// ...
});

const destinationHandler = () => {
	state.listenDestination = true;
};

const sourceHandler = () => {
	state.listenSource = true;
};

const back = () => {
	router.back();
};
</script>

<style>
.active-list-item {
	background-color: white; /* You can customize the background color */
}
</style>
