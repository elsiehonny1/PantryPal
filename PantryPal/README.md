# PantryPal

Console app that tracks pantry food (with expiration dates) and a weekly meal plan.
One generic `LinkedChain<T>` stores both `FoodItem`s and `Recipe`s.

## How to compile and run

Put all `.java` files in one folder / project. You need JUnit and the CS 2114
`student` library (the one Web-CAT uses) on the classpath for the tests.

**IDE (Eclipse / IntelliJ / VS Code):**
- Run program: right click `PantryPal.java` -> Run As -> Java Application
- Run tests: right click the project (or a single `*Test.java`) -> Run As -> JUnit Test

**Command line** (swap in your jar names; use `;` instead of `:` on Windows):

    javac -cp ".:junit.jar:student.jar" *.java
    java PantryPal
    java -cp ".:junit.jar:student.jar:hamcrest.jar" org.junit.runner.JUnitCore \
      NodeTest LinkedChainTest FoodItemTest RecipeTest ValidatorTest \
      PantryTest MealPlanTest PantryPalTest

Menu: 1 Add food, 2 Remove food, 3 View pantry, 4 Add recipe, 5 Remove recipe,
6 View meal plan, 7 View shopping list, 8 Quit (Ctrl+D also exits cleanly).
Dates are MM/DD/YYYY. Recipe ingredients are comma-separated.
The shopping list includes each distinct meal-plan ingredient that is not in
the pantry.

## Reflection

### What changed from our specification?
- Menu: We chose seven menu options based on what Pantry and MealPlan support.
- Name validation: Pantry and MealPlan clean names using Validator.cleanName. Invalid names throw an exception, while valid names that don't exist return false or 0. PantryPal also cleans names before passing them along.
- FoodItem equality depends on name and date, while Recipe equality depends on name.
- Today's date is not considered past, and already-expired food gets a warning instead of being rejected.
- LinkedChain.add(null) throws an exception, while other operations return false or 0 for null. 

### What changed in the LinkedChain?
- We did not change the way we used the LinkedChain. Our use of generics allows FoodItem and Recipe objects to be stored without needing separate linked chain implementations.

### What happened to our unknowns?
- Used GenAI to write proper tests to cover all cases including edge cases.
- FoodItem equality depends on name and date, while Recipe equality depends on name.

### What doesn't work yet?
(Missing MVP features, bad-input cases, missing or failing tests.)
- Manually testing every single bad-input to ensure that they all function properly.

## Next Steps

1. Stretch goals
    - "Can I make this?" feature that checks a recipe's ingredients against the pantry and says what's missing
    - Make a shopping list from all the missing ingredients in the meal plan
    - Use-it-first alert: lists items expiring within N days, soonest first.
    - Save and load: keeps the pantry and meal plan in a text file between runs.

2. New tests needed if stretch goals implemented

## Attribution

| File | Who | How |
|------|-----|-----|
| Node.java, LinkedChain.java | | provided starter / adapted |
| Validator.java, PantryPal.java | Blake Adkins | Validator with help of Claude and spec to understand what should be checked, PantryPal with use of Claude to ensure it runs all the classes properly, stretch goal shopping list written by Codex|
| FoodItem.java, Recipe.java | Elsie Honny | Used Claude to generate the classes|
| Pantry.java, MealPlan.java | Ayaan Deshmukh | Used Claude to generate the classes|
| NodeTest, LinkedChainTest, FoodItemTest, RecipeTest, ValidatorTest, PantryTest, MealPlanTest, PantryPalTest | Haniya Naseer | Used Claude to generate proper tests based on the spec document|

Note: Claude was used to ensure that all files functioned with each other and that the entire project worked as intended.
