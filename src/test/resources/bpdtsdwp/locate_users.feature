Feature: Locate users
  Scenario: Locate users listed as living in London, or whose coordinates are within 50 miles of London
    When I locate users
    Then I receive exactly these users in any order:
    | id  | first_name  | last_name   |
    | 135 | Mechelle    | Boam        |
    | 396 | Terry       | Stowgill    |
    | 520 |  Andrew     | Seabrocke   |
    | 658 |  Stephen    | Mapstone    |
    | 688 |  Tiffi      | Colbertson  |
    | 794 |  Katee      | Gopsall     |
    | 266 |  Ancell     | Garnsworthy |
    | 322 |  Hugo       | Lynd        |
    | 554 |  Phyllys    | Hebbs       |