Feature: PetStore API

  Scenario: Add a new pet and verify
    Given I create a new pet with ID "12345" and name "doggie"
    Then I verify the pet with ID "12345" exists
    When I update the status of pet with ID "12345" to "sold"
    Then I verify the pet with ID "12345" has status "sold"
    When I delete the pet with ID "12345"
    Then I verify the pet with ID "12345" is deleted
