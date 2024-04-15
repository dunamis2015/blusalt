#Build/Run Instruction
Clone the project, navigate to the root of the project and run the docker command below in your terminal.
docker-compose up

Postman Collection- https://api.postman.com/collections/29509055-917ca98e-da79-4e83-b29a-8bc1cf67c365?access_key=PMAT-01HVFYT5PQZ7QS21MP2FBFY9K3

Documentation- https://documenter.getpostman.com/view/29509055/2sA3Bj8DxK

Important Information to Note:
1. Call getDroneModels endpoint to get the pre-defined list of drone models available on the system
2. Call registerDrone endpoint to register a new drone. You will need to supply a valid drone model based on the response from (1) above.
3. Call initiateDroneLoading endpoint to load medications on a particular drone.
   You will need to pass a unique loadingReference for this request. The drone state will change to "LOADING" when you make this call.
4. Once the loading is completed, call completeDroneLoading endpoint with the same loadingReference used above to complete the loading process.
   The state of the drone will change to "LOADED".
   You can add more medications to the drone if it is not fully loaded provided it can still accommodate the weight of the new medications. Repeat step 3 & 4 to do this.
6. Call the fireDrone endpoint to fire the loaded drone to the delivery address/location. The state of the drone changes to "DELIVERING"
7. There is a background task that updates the state of the drone to "RETURNING" when it's returning from the delivery address.
8. Once the drone gets back to the initial take-off point, the background task update the state back to "IDLE" while the medication status is updated to "DELIVERED".
9. Call getDroneLoadedItems endpoint to retrieve loaded medications on a drone
10. Call getAvailableDrones endpoint to get a list of IDLE drones.
11. Call getDroneBatteryLevel endpoint to retrieve the battery level of a particular drone.
12. A background task run every 30 minutes to track the battery level of all drones and log it on the Log table
