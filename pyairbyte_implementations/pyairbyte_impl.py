import airbyte as ab
import pandas as pd

# Path to your local JSON file
json_file_path = "data.json"

# Configure the File source
source = ab.get_source(
    "source-file",
    config={
        "dataset_name": "example_data",
        "format": "json",
        "files": [
            {
                "url": f"file://{json_file_path}"
            }
        ]
    }
)

# Read records into a PyAirbyte stream
streams = source.read()

# Convert the first stream to a pandas DataFrame
df = streams["example_data"].to_pandas()

# Write to CSV
df.to_csv("output.csv", index=False)

print("JSON data written to output.csv")