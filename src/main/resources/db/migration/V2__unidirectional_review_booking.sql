-- Booking <-> Review is now UNIDIRECTIONAL with Review as the owning side.
-- The foreign key moves FROM booking (booking_review_id / passenger_review_id)
-- TO review (booking_id). We add the new column, backfill it from the old
-- booking-side links, enforce NOT NULL, add the FK, then drop the old columns.

-- 1. Add the new owning FK column. Nullable for now so the backfill can run.
alter table review
    add column booking_id bigint;

-- 2. Backfill review.booking_id from the old booking-side links (if any rows
--    exist). A booking pointed at its review via booking_review_id /
--    passenger_review_id; we flip that so the review points at its booking.
update review r
    join booking b on b.booking_review_id = r.id
    set r.booking_id = b.id;

update review r
    join booking b on b.passenger_review_id = r.id
    set r.booking_id = b.id;

-- 3. Every review must belong to a booking -> enforce NOT NULL.
--    (Fails if any review row has no resolvable booking; clean up orphans first.)
alter table review
    modify booking_id bigint not null;

-- 4. Add the foreign key: review.booking_id -> booking.id.
alter table review
    add constraint fk_review_booking
    foreign key (booking_id) references booking (id);

-- 5. Drop the now-unused booking-side FK columns (and their constraints).
alter table booking drop foreign key fk_booking_booking_review;
alter table booking drop foreign key fk_booking_passenger_review;
alter table booking drop column booking_review_id;
alter table booking drop column passenger_review_id;
