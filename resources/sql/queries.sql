-- :name new-task :! :n
-- :doc create a new task using the title, description and time keys
insert into todo
            (title, description)
values (:title, :description)

-- :name list-task :* :?
-- :doc list all available task
select * from todo

-- :name delete-task :!
-- :doc delete a task by it id
delete from todo
where id = :id


