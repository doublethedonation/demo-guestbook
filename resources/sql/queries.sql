-- :name save-message! :n
insert into guestbook
(name, message, subject)
values (:name, :message, :subject)

-- :name get-messages :? :*
select * from guestbook