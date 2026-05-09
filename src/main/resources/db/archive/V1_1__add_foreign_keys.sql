-- Archivada: ya no se aplica por Flyway (rompía esquemas nuevos o chocaba con ddl-auto).
-- Referencia histórica. La FK defects.coffee_id → coffees queda obsoleta con el modelo actual.

ALTER TABLE defects
    ADD CONSTRAINT FK_defects_coffee_id
        FOREIGN KEY (coffee_id)
        REFERENCES coffees (id);

ALTER TABLE profiles
    ADD COLUMN user_id BIGINT;

ALTER TABLE profiles
    ADD CONSTRAINT FK_profiles_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id);


ALTER TABLE recipes
    ADD CONSTRAINT FK_recipe_user_id
        FOREIGN KEY (user_id)
            REFERENCES profiles (id);

ALTER TABLE ingredients
    ADD CONSTRAINT FK_ingredients_recipe_id
        FOREIGN KEY (recipe_id)
            REFERENCES recipes (id);
