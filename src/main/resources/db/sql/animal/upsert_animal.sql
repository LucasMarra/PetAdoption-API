INSERT INTO tb_animal (
   id,
   external_id,
   name,
   description,
   image_url,
   category,
   status,
   created_at,
   updated_at
)
VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (external_id)
DO
UPDATE SET name = excluded.name,
    description = excluded.description,
    image_url = excluded.image_url,
    category = excluded.category,
    updated_at = now()