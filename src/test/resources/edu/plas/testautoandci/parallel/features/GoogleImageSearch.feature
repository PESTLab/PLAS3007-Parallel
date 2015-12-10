@imageSearch
Feature: Google Image Search

  @animalImages @lecture10
  Scenario Outline: Image search
    Given I navigate to https://www.google.com/imghp
    When I search for '<searchText>' on Google Images
    Then there are '10 or more' images

  Examples:
    | searchText                         |
    | most colourful monkey in the world |
    | boring zebra                       |