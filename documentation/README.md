# YoUber README

## Introduction

YoUber is a ride-sharing platform connecting travelers and drivers for convenient and efficient journeys. YoUber simplifies travel by connecting travelers and drivers heading in the same direction. It promotes eco-friendly transportation, efficiency, and community.

## Using the Software

### Prerequisites

Before you begin, ensure you have the following requirements installed:

- Node.js version 18
- Java

### Usage

#### Description per Webpage

- **Home Page:** Landing page, where there are two CTA buttons, one for signing up to drive and other for requesting a ride. Users can also consult community guidelines and safety regulations along with site navigation.
- **Get a Ride:** Get a ride page, where users can use a map or text inputs to choose locations for the trip along with number of seats and date.
- **Sign Up as driver:** Sign Up as driver page, where users can sign up as drivers by uploading car and personal information.
- **Trips:** Trips page, where both users and drivers can consult their past and upcoming trips and their details. 

#### Screenshots

![Home Page](/images/landing.png)
*Caption: The welcoming home page of our application.*

![Get a Ride](/images/get_ride.png)
*Caption: Page where users request rides.*

![Sign up as driver](/images/my_trips.png)
*Caption: Overview of past and future trips.*

![Trips](/images/signup_as_driver.png)
*Caption: Sign up form for the driver.*

## Getting Started

### Viewing the App

To access the live application, visit [the website](https://project-2.ti.howest.be/2023-2024/group-13/).

### Local Development

#### Running the Client

1. Clone the repository: `git clone https://gitlab.ti.howest.be/ti/2023-2024/s3/analysis-and-development-project/projects/group-13/client.git`
2. Navigate to the client folder: `cd client`
3. Install dependencies: `npm install`
4. Start the client in development: `npm run dev`
5. Navigate to localhost with suggested port: http://localhost:5173/

#### Running the Server

1. Clone the repository: `git clone https://gitlab.ti.howest.be/ti/2023-2024/s3/analysis-and-development-project/projects/group-13/server.git`
2. Navigate to the server folder: `cd server`
3. Run the server

## Code Quality

For insights into the codebase quality, refer to our SonarQube analysis.
- Server: https://sonarqube.ti.howest.be/dashboard?id=2023.project-2%3Aadria-server-13
- Client: https://sonarqube.ti.howest.be/dashboard?id=2023.project-2%3Aadria-client-13

## Understanding the Documentation Repository

### Folder Structure

Our repository follows a structured organization:

- **/server:** Repository for Vert.x server.
- **/client:** Repository for Vue.js client.
- **/documenation:** Repository for documentation.

The **client** repository has 2 branches:

- **main:** Branch where the client gets deployed from containing /src directory which holds built Vue.js project.
- **dev:** Branch where Vue.js project goes.

### Wireframes

Browse the project wireframes [here](https://www.figma.com/file/b54N9MgGWlwrM57961OD51/YoUber-Wireframes?type=design&node-id=3%3A8&mode=design&t=ugY2vggLr30KqZRy-1).

### Use Case Diagram

Consult our use case diagram [here](https://miro.com/app/board/uXjVNW4qr0w=/?share_link_id=414697979812).

### Business Model Canvas

Consult our BMC [here](https://miro.com/app/board/uXjVMjSXZng=/?share_link_id=656812832578)

### Business Case

Consult our business case [here](https://docs.google.com/document/d/1hI6dsHiKXKe1Tpojp-heUk4GOe5aBpItuncyOca70dI/edit)

### C4 Diagram

See our C4 diagram [here](https://miro.com/app/board/uXjVNW4qr38=/?share_link_id=29077509590)


### Entity Relation Diagram

See our ERD [here](https://lucid.app/lucidchart/7b5cd277-d34d-4e53-9b25-0381dc4e2bde/edit?viewport_loc=-941%2C-644%2C2958%2C1409%2C0_0&invitationId=inv_77ed7ec5-bf11-49bd-835f-3162e7154be4)

### Flowcharts

See our flowcharts [here](https://miro.com/app/board/uXjVNZOWdhM=/?share_link_id=378249936259)


## Implemented Topics

### Self-Study Topics

- Vue.js
- Vuetify
- Firebase
- TailwindCSS
- OpenStreetMap (for some students)

### Class Taught Topics

- OpenStreetMap (for some students)
- WebSockets

## The Team!

Let's meet the brilliant minds behind YoUber:

- **Bombeke Stephen:** [Project Manager, Developer]
  - Main Contributions: [Server, Client, Documentation, Business Case]

- **Vashakmadze Luka:** [Developer]
  - Main Contributions: [Client, Documentation, Business Case]

- **Verstraete Evarist:** [Developer]
  - Main Contributions: [Server, Client, Documentation]
  
- **Moeyaert Lowie:** [Developer]
  - Main Contributions: [Client, Documentation, Business Case]

- **Badisco Jordy:** [Developer]
  - Main Contributions: [Server, Documentation, Business Case]


  ## Code reviews
  ### Fiend Yde
  #### **FirebaseRepository - Serverside scope** ####
  ---
  **Positive:**
  - Everything looks very good.

  **Tips for improvement:**
  - Removing the unused methods in that class.

  #### **GetRide - Client Side** ####
  --- 
  **Positive:**
  - Everything is well implemented.

  **Tips for improvement:**
  - Nothing.

  #### **MyTrips - Client Side** ####
  --- 
  **Positive:**
  - Everything is well implemented.

  **Tips for improvement:**
  - /
  ### **Tim Michielssen**
  #### **FirebaseRepository - Serverside scope** ####
  ---
  **Positive:**
  - Everything looks very good.

  **Tips for improvement**
  - Removing the unused methods in that class.
  - extract methods in a different class -> human readable

  #### **GetRide - Client Side** ####
  --- 
  **Positive:**
  - Everything is good implemented.

  **Tips for improvement:**
  - Remove unused Imports.

  #### **MyTrips - Client Side** ####
  --- 
  **Positive:**
  - Everything is well implemented.

  **Tips for improvement:**
  - /

  ## Users tests

  ### Thirdyear's students:

  - Miguel De Backer
  - Dieter Bequet
  - Ward Decoster
  - Hanczyc Bartlomiej

  ### Modifications
  
  ---

  #### Homepage

  - First section, buttons must have a better name.
  
  #### GetRide page

  - When trip is assigned, you should redirected to the trip details page.
  - Amount of seats buttons must be more clearly.
  - Date & Time must be seperated (not necessary, just a preference).
  - When an address doesn't exist, there should be given an error message.
  - Instead of an hamburgermenu, you should create a normal header (Personal preference).
  - When you want a ride right now, the user should be able to have the settings sets op localDateTime.
