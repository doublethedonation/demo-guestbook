-- :name save-message! :n
insert into guestbook
(name, message, subject)
values (:name, :message, :subject)

-- :name get-messages :? :*
select * from guestbook

-- :name delete-message! :!
delete from guestbook
where id = :id
