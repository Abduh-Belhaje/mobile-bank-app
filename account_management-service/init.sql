-- Create the sequence
CREATE SEQUENCE IF NOT EXISTS account_sequence START 10245807;

-- Associate the sequence with the id column of the account table
ALTER TABLE account ALTER COLUMN account_id SET DEFAULT nextval('account_sequence');
