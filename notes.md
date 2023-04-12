# Note

## Multiple keys for single value

If I would like to store in HashOperation with both key `user_name` and `phone_num` with same
value `user_age, user_gender, user_identity...etc`, but I don't wanna duplicate the value, here might be the solution:

- key, value pairs below:
  - `user_name`, `user_id`
  - `phone_num`, `user_id`
  - `user_id`, `user_age, user_gender, user_identity...etc` 


```
Maybe have an intermediate key:
- user_10 → id_123
- driver_5 → id_123
- id_123 → data_that_you_dont_want_to_duplicate
```

ref: https://stackoverflow.com/questions/44323454/multiple-keys-pointing-to-a-single-value-in-redis-cache-with-java