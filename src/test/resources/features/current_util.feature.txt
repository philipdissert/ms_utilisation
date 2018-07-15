Feature: Current utilization of the learning space
	As a guest
	I want to know the current utilization of the learning space
	So that I can decide to go there if there is an available space

Background:
	Given I am on the “CurrentState” page

Scenario: Learning space is open
	Given the learning space is not closed
	Then the front page displays the current utilization of the learning space
	And the front page displays a room plan with the PCs and their current state

Scenario: Learning space is closed
	Given the learning space is closed
	Then the page displays “Learning space is currently closed”
	And the page displays the usual opening hours of the learning space

Scenario: Page is opened longer than 10 minutes
	Given the current page is opened longer than 10 minutes
	Then the page refreshes
	And the new data with the current utilization is displayed