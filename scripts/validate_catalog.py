#!/usr/bin/env python3
"""Validate the editorial station catalog without external dependencies."""
from pathlib import Path
import csv

FILE = Path(__file__).resolve().parents[1] / "app/src/main/assets/catalog/stations_editorial.csv"
REQUIRED = {"name", "frequency", "band", "province", "municipality", "category", "official_source", "notes"}

with FILE.open(encoding="utf-8", newline="") as f:
    rows = list(csv.DictReader(f))

if not rows:
    raise SystemExit("Catalog is empty")
missing = REQUIRED - set(rows[0])
if missing:
    raise SystemExit(f"Missing columns: {sorted(missing)}")

names = [r["name"].strip().lower() for r in rows]
duplicates = sorted({n for n in names if names.count(n) > 1})
if duplicates:
    raise SystemExit(f"Duplicate station names: {duplicates}")

for i, row in enumerate(rows, 2):
    if not row["name"].strip():
        raise SystemExit(f"Row {i}: empty name")
    if row["band"] and row["band"] not in {"AM", "FM"}:
        raise SystemExit(f"Row {i}: band must be AM or FM")

print(f"OK: {len(rows)} editorial station records validated")
