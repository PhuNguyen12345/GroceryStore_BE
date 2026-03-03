alter table suppliers
    add column is_active boolean default true;

alter table customers
    add column is_active boolean default true;

alter table brands
    add column is_active boolean default true;

alter table product_units
    add column is_active boolean default true;

alter table warehouses
    add column is_active boolean default true;

alter table shifts
    add column is_active boolean default true;