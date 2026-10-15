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
6 View meal plan, 7 Quit (Ctrl+D also exits cleanly).
Dates are MM/DD/YYYY. Recipe ingredients are comma-separated.

## Reflection

(Fill in with your real answers. Say where each change came from: group, GenAI, or own reflection.)

### What changed from our specification?
- Pantry and MealPlan do the name cleaning through `Validator.cleanName`, so invalid names throw `IllegalArgumentException` and valid-but-missing names return false/0, as the spec said.
- (Add anything your team actually changed: signatures, where validation lives, test cases.)

### What changed in the LinkedChain?
(Used unchanged / what you added and why.)

### What happened to our unknowns?
(What you figured out vs what is still open.)

### What doesn't work yet?
(Missing MVP features, bad-input cases, missing or failing tests.)

## Next Steps

1. (Remaining MVP work in order, with an owner for each)
2. Stretch goals: committed = ..., dropped = ...
3. New tests needed: ...

## Attribution

| File | Who | How |
|------|-----|-----|
| Node.java, LinkedChain.java | | provided starter / adapted |
| Validator.java, PantryPal.java | Blake Adkins | |
| FoodItem.java, Recipe.java | Elsie Honny | |
| Pantry.java, MealPlan.java | Ayaan Deshmukh | |
| NodeTest, LinkedChainTest, FoodItemTest, RecipeTest, ValidatorTest, PantryTest, MealPlanTest, PantryPalTest | Haniya Naseer | |
