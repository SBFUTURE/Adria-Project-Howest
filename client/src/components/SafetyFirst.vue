<script setup>
import { ref } from "vue";

let dialog = ref(false);
let community = ref(false);
</script>
<template>
	<div class="relative something bl-black">
		<v-parallax
			class="opacity-50 z-50 bg-black w-full h-[75vh]"
			alt="Driver picture"
			src="https://i.ibb.co/K9hFZm9/Security-Office-Picture.jpg">
		</v-parallax>
		<div>
			<div
				class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2 text-white text-center">
				<div class="flex justify-center items-center flex-col">
					<h1 class="uppercase font-bold text-white text-6xl">Safety first</h1>
					<h2 class="font-bold text-white text-2xl mb-10">
						Your safety comes first. Always!
					</h2>
					<p class="w-1/2 text-white text-center mb-10">
						We look out for you before, during, and after every single ride. And
						we monitor rides to help you get where you need to go. If you ever
						need it, we’re standing by, ready to help.
					</p>
				</div>
				<div>
					<template>
						<v-row justify="center">
							<v-dialog
								v-model="dialog"
								persistent
								width="auto">
								<v-card>
									<v-card-title class="text-h5"> Safety First! </v-card-title>
									<!-- TODO:: put some meaningfull text.-->
									<v-card-text
										>Friendship, dedication and SafetyFirst</v-card-text
									>
									<v-card-actions>
										<v-spacer></v-spacer>
										<v-btn
											class="bg-black"
											variant="outlined"
											size="large"
											color="white"
											@click="dialog = false">
											Disagree
										</v-btn>
										<v-btn
											class="bg-black"
											variant="outlined"
											size="large"
											color="white"
											@click="dialog = false">
											Agree
										</v-btn>
									</v-card-actions>
								</v-card>
							</v-dialog>
						</v-row>
					</template>
					<template>
						<v-row justify="center">
							<v-dialog
								v-model="community"
								width="800">
								<template v-slot:activator="{ props }">
									<v-btn
										color="primary"
										v-bind="props">
										Open Dialog
									</v-btn>
								</template>
								<v-card>
									<v-card-title>
										<span class="text-h5">YoUber community guidelines</span>
									</v-card-title>
									<v-card-text>
										<p>
											Welcome to our thriving car sharing community. We are
											dedicated to ensuring a safe, respectful, and enjoyable
											environment for all our members. Please carefully review
											and adhere to the following comprehensive guidelines to
											promote a positive and responsible community:
										</p>
										<br />
										<h2>1. Respect for All Members:</h2>
										<ul>
											<li>
												Respect and kindness are the cornerstones of our
												community. Treat all members, whether drivers or
												passengers, with politeness and empathy.
											</li>
											<li>
												Do not engage in offensive, abusive, discriminatory, or
												harmful behavior or language, either in your
												interactions or through communication on our platform.
											</li>
										</ul>
										<br />
										<h2>2. Prioritize Safety:</h2>
										<ul>
											<li>
												Safety is our top priority. Always obey local traffic
												laws, regulations, and safety recommendations.
											</li>
											<li>
												As a driver, ensure your car is in excellent working
												condition and regularly serviced. Passengers should also
												consider the safety of the ride.
											</li>
										</ul>
										<br />
										<h2>3. Effective Communication:</h2>
										<ul>
											<li>
												Maintain open and transparent communication with other
												community members. This includes sharing clear trip
												details, discussing pickup and drop-off locations, and
												addressing any changes in plans.
											</li>
											<li>
												Respond to messages and inquiries from other users in a
												timely and respectful manner.
											</li>
										</ul>
										<br />
										<h2>4. Honesty and Transparency:</h2>
										<ul>
											<li>
												Be forthright and transparent in your dealings. Provide
												accurate information about your car's condition,
												mileage, and other relevant details to prevent
												misunderstandings.
											</li>
											<li>
												Disclose any known issues, limitations, or special
												conditions of your car before sharing it with others.
											</li>
										</ul>
										<br />
										<h2>5. Punctuality:</h2>
										<ul>
											<li>
												Be punctual for your scheduled trips, whether you are
												the driver or passenger. Respect the agreed-upon pickup
												and drop-off times to ensure a smooth experience for
												everyone.
											</li>
										</ul>
										<br />
										<h2>6. Cleanliness and Maintenance:</h2>
										<ul>
											<li>
												Maintain the shared car in a clean and organized state.
												Leave the vehicle as clean as or cleaner than you found
												it.
											</li>
											<li>
												Report any mess, damage, or maintenance issues
												immediately. If you are responsible for any damage,
												discuss the matter with the owner.
											</li>
										</ul>
										<br />
										<h2>7. Privacy and Security:</h2>
										<ul>
											<li>
												Respect the privacy and security of car owners and
												fellow passengers. Do not share personal information
												without consent.
											</li>
											<li>
												In case of any safety concerns, suspicious behavior, or
												security issues, promptly report these matters to the
												platform administrators.
											</li>
										</ul>
										<br />
										<h2>8. Ratings and Reviews:</h2>
										<ul>
											<li>
												After each trip, provide constructive and honest
												feedback through ratings and reviews. This feedback
												system promotes accountability and trust within the
												community.
											</li>
											<li>
												Refrain from leaving false, misleading, or inappropriate
												feedback. Remember, constructive criticism can help
												fellow members improve.
											</li>
										</ul>
										<br />
										<h2>9. Non-Commercial Use:</h2>
										<ul>
											<li>
												Use the platform exclusively for personal car-sharing
												purposes. Avoid utilizing it for commercial or
												profit-driven activities, or offer services unrelated to
												car sharing.
											</li>
										</ul>
										<br />
										<h2>10. Conflict Resolution:</h2>
										<ul>
											<li>
												In the event of conflicts or disputes, make a genuine
												effort to resolve issues through open and respectful
												communication and compromise.
											</li>
											<li>
												If an amicable resolution cannot be reached, do not
												hesitate to contact our platform's support team for
												guidance and assistance.
											</li>
										</ul>
										<br />
										<h2>11. Compliance with Platform Policies:</h2>
										<ul>
											<li>
												Abide by the platform's terms of service, policies, and
												community guidelines. Familiarize yourself with our
												rules and adhere to them.
											</li>
											<li>
												Violations of these guidelines may result in temporary
												or permanent suspension from the platform.
											</li>
										</ul>
										<br />
										<p>
											By diligently adhering to these community guidelines, you
											contribute to the creation of a welcoming, safe, and
											pleasant car sharing community. Your active participation
											is pivotal in maintaining the integrity of our platform
											and fostering trust among all our members. Thank you for
											being a valued part of our car sharing community!
										</p>
									</v-card-text>
									<v-card-actions>
										<v-spacer></v-spacer>
										<v-btn
											class="bg-black"
											variant="outlined"
											size="large"
											color="white"
											@click="community = false">
											Disagree
										</v-btn>
										<v-btn
											class="bg-black"
											variant="outlined"
											size="large"
											color="white"
											@click="community = false">
											Agree
										</v-btn>
									</v-card-actions>
								</v-card>
							</v-dialog>
						</v-row>
					</template>
					<v-btn
						class="ml-4"
						size="x-large"
						variant="outlined"
						@click="dialog = true"
						>Learn about safety</v-btn
					>
					<v-btn
						class="ml-4"
						size="x-large"
						variant="outlined"
						@click="community = true"
						>Community guidelines</v-btn
					>
				</div>
			</div>
		</div>
		<div class="custom-shape-divider-top-1700043096">
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
.custom-shape-divider-top-1700043096 {
	position: absolute;
	top: 0;
	left: 0;
	width: 100%;
	overflow: hidden;
	line-height: 0;
}

.custom-shape-divider-top-1700043096 svg {
	position: relative;
	display: block;
	width: calc(219% + 1.3px);
	height: 167px;
}

.custom-shape-divider-top-1700043096 .shape-fill {
	fill: black;
}
</style>
