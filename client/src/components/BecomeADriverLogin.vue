<script setup>
import { ref } from "vue";
import { useField, useForm } from "vee-validate";
import { router } from "../main";
import { becomeDriver } from "../utils/Utils";

const dialog = ref(false);

const finish = () => {
	router.push("/2023-2024/group-13/trips");
};

const { handleSubmit, handleReset } = useForm({
	validationSchema: {
		brand(value) {
			if (value?.length >= 3 && /[A-Z-a-z]/.test(value)) return true;

			return "Name needs to be at least 3 characters.";
		},
		licensePlate(value) {
			if (
				value?.length >= 7 &&
				value?.length < 8 &&
				/[0-9]+[A-Z-a-z]+[0-9]/.test(value)
			)
				return true;

			return "Please, give a valid license plate example 1abc123";
		},
		year(value) {
			if (value?.length <= 4 && value > 1970 && /[0-9]/.test(value))
				return true;

			return "Give a valid year";
		},
	},
});
const brand = useField("brand");
const licensePlate = useField("licensePlate");
const modal = useField("modal");
const year = useField("year");
const passport = useField("passport");
const driverLicense = useField("driverLicense");

const submit = handleSubmit((formData) => {
	const adriaId = JSON.parse(localStorage.getItem("user"));
	becomeDriver(adriaId, formData);
	localStorage.setItem("isDriver", JSON.stringify(true));
	dialog.value = true;
});
</script>
<template>
	<div class="relative something bl-black">
		<v-parallax
			class="opacity-50 z-50 bg-black h-[110vh]"
			alt="Drivers picture"
			src="https://i.ibb.co/Fwh4n5C/limousine-chauffeur.jpg">
		</v-parallax>
		<div
			class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 w-50 ml-auto mr-auto mb-20 mt-20 text-white pa-14 rounded">
			<h1 class="text-7xl font-bold text-center uppercase mb-10">
				Become a driver
			</h1>
			<form @submit.prevent="submit">
				<div class="flex flex-row justify-around items-center gap-x-10">
					<v-text-field
						v-model="brand.value.value"
						:error-messages="brand.errorMessage.value"
						label="Brand"></v-text-field>

					<v-text-field
						v-model="licensePlate.value.value"
						:counter="7"
						:placeholder="'1abc123'"
						:error-messages="licensePlate.errorMessage.value"
						label="License plate"></v-text-field>
				</div>
				<div class="flex flex-row justify-around items-center gap-x-10">
					<v-text-field
						v-model="modal.value.value"
						:error-messages="modal.errorMessage.value"
						label="Modal"></v-text-field>
					<v-text-field
						v-model="year.value.value"
						:error-messages="year.errorMessage.value"
						label="Year"></v-text-field>
				</div>
				<div class="flex flex-row justify-around items-center gap-x-10">
					<v-file-input
						v-model="passport.value.value"
						label="Passport"></v-file-input>
					<v-file-input
						v-model="driverLicense.value.value"
						label="Driver's license "></v-file-input>
				</div>
				<div class="w-full">
					<div class="flex">
						<v-btn
							class="flex-1 mr-8"
							variant="outlined"
							size="x-large"
							type="submit">
							Save
						</v-btn>

						<v-btn
							@click="handleReset"
							variant="outlined"
							class="flex-1"
							size="x-large">
							clear
						</v-btn>
					</div>
				</div>
			</form>
		</div>
		<v-dialog
			v-model="dialog"
			width="auto">
			<v-card class="bg-grey-darken-4">
				<v-card-text>
					You have successfully registered as driver! You can now add rides from
					my trips page!</v-card-text
				>
				<v-card-actions>
					<v-btn
						color="white"
						block
						@click="finish"
						>Finish</v-btn
					>
				</v-card-actions>
			</v-card>
		</v-dialog>
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
</style>
