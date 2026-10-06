#!/usr/bin/env python3
"""Copy the versioned editorial catalog into Android assets."""
from pathlib import Path
import shutil

ROOT = Path(__file__).resolve().parents[1]
source = ROOT / "catalog" / "stations.csv"
target = ROOT / "app" / "src" / "main" / "assets" / "catalog" / "stations_editorial.csv"
source_registry = ROOT / "catalog" / "source_registry.json"
target_registry = ROOT / "app" / "src" / "main" / "assets" / "catalog" / "source_registry.json"

if not source.exists():
    raise SystemExit(f"Missing source catalog: {source}")
if not source_registry.exists():
    raise SystemExit(f"Missing source registry: {source_registry}")

target.parent.mkdir(parents=True, exist_ok=True)
shutil.copy2(source, target)
shutil.copy2(source_registry, target_registry)
print(f"Synced catalog -> {target}")
print(f"Synced sources -> {target_registry}")
