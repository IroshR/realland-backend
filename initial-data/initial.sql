INSERT INTO `merchant` (`merchant_id`, `address`, `category_id`, `created_date`, `email`, `latitude`, `longitude`, `monthly_subscription_amount`, `name`, `registration_no`, `review_count`, `review_rate`, `status`, `subs_package_id`, `subscription_expiry_date`, `telephone`, `telephone2`, `type_id`, `web_sites`)
VALUES ('1','Colombo', '1', now(), 'Test@bro', '6', '6', '0', 'Real TT', 'TTTTTT', '0', '0.00000', '2', '0', '2020-03-24 22:37:27', '1231231232', '3453453454', '1', 'www.tt.com');

INSERT INTO `admin_user` (`admin_user_id`, `created_date`, `email`, `full_name`, `is_notification_enable`, `is_password_change_required`, `login_name`, `merchant_id`, `mobile`, `password`, `profile_image_url`, `status`, `type_id`) VALUES ('1', '2020-03-24 22:31:55', 'Test@tt.com', 'Test Bro', '0', '0', 'c', '1', '1231231232', '124', '1', '2', '1');

