<template>
	<v-row justify="center">
		<v-dialog
			v-model="dialog"
			persistent
			width="1024">
			<v-card>
				<v-card-title>
					<h2 class="text-h5 mt-2 text-center">Login with AdriaID</h2>
				</v-card-title>
				<v-card-text>
					<v-container>
						<v-row>
							<v-col cols="12">
								<v-text-field
									v-model="textFieldValue"
									:enabled="!checkboxValue"
									:label="labelText"
									type="AdriaID"
									:error="loginError && !textFieldValue"
									variant="outlined"
									required></v-text-field>
							</v-col>
							<v-row class="mx-2">
								<v-checkbox
									v-model="checkboxValue"
									label="Login with host AdriaID (AdriaID from the bracelet)"
									on></v-checkbox>
								<small class="text-center flex justify-center items-center">
									* indicates required field</small
								>
							</v-row>
						</v-row>
					</v-container>
				</v-card-text>
				<v-card-actions>
					<v-spacer></v-spacer>
					<v-btn
						color="black"
						variant="text"
						@click="handleSave"
						>Log In</v-btn
					>
					<v-spacer></v-spacer>
				</v-card-actions>
			</v-card>
		</v-dialog>
	</v-row>
</template>

<script>
import { ref, watch, computed, onMounted } from "vue";
import { authenticate } from "../utils/Utils";

export default {
	props: {},
	setup() {
		const dialog = ref(false);
		const checkboxValue = ref(false);
		const textFieldValue = ref("");
		const loginError = ref(false);

		const labelText = computed(() => {
			if (loginError.value && !textFieldValue.value) {
				return "Enter Valid AdriaID*";
			} else {
				return "AdriaID*"; // Default label
			}
		});

		onMounted(() => {
			const user = JSON.parse(localStorage.getItem("user"));
			if (!user) {
				dialog.value = true;
			}
		});

		watch(checkboxValue, (newVal) => {
			if (!newVal) {
				// If checkbox is unchecked, reset the text field value
				textFieldValue.value = "";
			} else {
				// If checkbox is checked, set the text field value to something
				const randomNum = Math.floor(Math.random() * (4 - 1 + 1) + 1);
				textFieldValue.value = "adria-" + randomNum;
			}
		});

		const handleSave = async () => {
			if (textFieldValue.value.length !== 0) {
				await authenticate(textFieldValue.value);
				localStorage.setItem("user", JSON.stringify(textFieldValue.value));
				dialog.value = false;
			} else {
				loginError.value = true;
			}
		};

		return {
			dialog,
			checkboxValue,
			textFieldValue,
			loginError,
			labelText,
			handleSave,
		};
	},
};
</script>
