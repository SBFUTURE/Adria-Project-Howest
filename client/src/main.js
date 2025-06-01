import { createApp } from 'vue'
import { createRouter, createWebHistory } from "vue-router";

import './style.css'
import App from './App.vue'

// Vuetify
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import Home from "./components/Home.vue";
import MyTrips from "./pages/MyTrips.vue";
import GetRide from "./pages/GetRide.vue";
import BecomeADriverPage from "./components/BecomeADriverPage.vue";

const vuetify = createVuetify({
    components,
    directives,
});

const routes = [
    { path: "/2023-2024/group-13/", component: Home },
    { path: "/2023-2024/group-13/trips", component: MyTrips },
    { path: "/2023-2024/group-13/get-a-ride", component: GetRide },
    { path: "/2023-2024/group-13/become-a-driver", component: BecomeADriverPage }
];

export const router = createRouter({
    history: createWebHistory(),
    routes,
});


createApp(App).use(vuetify).use(router).mount('#app')

