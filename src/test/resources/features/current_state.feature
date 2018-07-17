Feature: Stationary Devices with their current state
	As a guest
	I want to know basic information about the stationary devices in the learning space
	So that I can decide whether I want to go to there


Scenario: PC change from Occupied to free
	Given 10 PC are/is currently 3 Linux occupied
	When 3 PC change to 1 Linux free
	Then 3 PC are/is currently 1 Linux free
	And 3 PC less are/is 3 occupied