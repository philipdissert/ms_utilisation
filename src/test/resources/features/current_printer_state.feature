Feature: Printer Queue
	As a Guest
	I want to know how much time it takes until I can print something
	So that I can decide whether I want to use the printer

Background:
	Given I am on the “CurrentState” page

Scenario: printer is not in use
	Given a printer is currently not in use
	Then the current amount of orders for this printer is “0”
	And the estimated time to print for this printer is “0”

Scenario: printer is in use
	When a printer is in use
	Then the current amount of orders for this printer is dis-played
	And the estimated time to print for this printer is dis-played