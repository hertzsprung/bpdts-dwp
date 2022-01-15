Feature: Locate users
  Scenario: Locate users listed as living in London, or whose coordinates are within 50 miles of London
    When I locate users
    Then I receive exactly these users in any order:
    | first_name | last_name  |
    | Mechelle   | Boam       |
    | Terry      | Stowgill   |
    | Andrew     | Seabrocke  |
    | Stephen    | Mapstone   |
    | Tiffi      | Colbertson |
    | Katee      | Gopsall    |
    | TODO       | TODO       |