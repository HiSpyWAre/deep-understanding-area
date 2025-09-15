print("==== NOTES APP ====")

# with open("note.txt", "w") as file:
#     file.write ()

#!/usr/bin/env python3
import os
import argparse
from datetime import datetime

# The directory where notes will be stored
NOTES_DIR = "notes"

def initialize():
    """Create the notes directory if it doesn't exist."""
    if not os.path.exists(NOTES_DIR):
        os.makedirs(NOTES_DIR)

def sanitize_title(title):
    """Converts a note title into a safe filename."""
    return title.lower().replace(" ", "-") + ".txt"

def desanitize_filename(filename):
    """Converts a filename back into a human-readable title."""
    return filename[:-4].replace("-", " ").title()

def create_note(args):
    """Creates a new note file."""
    filename = sanitize_title(args.title)
    path = os.path.join(NOTES_DIR, filename)

    if os.path.exists(path):
        print(f"Error: A note with the title '{args.title}' already exists.")
        return

    timestamp = datetime.now().strftime("%Y-%m-%d %H:%M:%S")
    full_content = f"Created: {timestamp}\n---\n{args.content}"

    with open(path, 'w', encoding='utf-8') as f:
        f.write(full_content)
    print(f"✅ Note '{args.title}' created successfully.")

def list_notes(args):
    """Lists all available notes."""
    files = [f for f in os.listdir(NOTES_DIR) if f.endswith(".txt")]
    if not files:
        print("No notes found.")
        return

    print("--- 📝 Your Notes ---")
    for f in sorted(files):
        print(f"- {desanitize_filename(f)}")

def read_note(args):
    """Reads and displays the content of a specific note."""
    filename = sanitize_title(args.title)
    path = os.path.join(NOTES_DIR, filename)

    if not os.path.exists(path):
        print(f"Error: Note '{args.title}' not found.")
        return

    with open(path, 'r', encoding='utf-8') as f:
        content = f.read()

    print(f"--- 📖 {args.title} ---\n")
    print(content)

def delete_note(args):
    """Deletes a specific note after confirmation."""
    filename = sanitize_title(args.title)
    path = os.path.join(NOTES_DIR, filename)

    if not os.path.exists(path):
        print(f"Error: Note '{args.title}' not found.")
        return

    try:
        confirm = input(f"Are you sure you want to delete '{args.title}'? [y/N]: ")
        if confirm.lower() == 'y':
            os.remove(path)
            print(f"🗑️ Note '{args.title}' has been deleted.")
        else:
            print("Deletion cancelled.")
    except KeyboardInterrupt:
        print("\nDeletion cancelled.")

def main():
    """Main function to handle argument parsing and command execution."""
    initialize()
    parser = argparse.ArgumentParser(description="A simple command-line note-taking app.")
    subparsers = parser.add_subparsers(dest='command', required=True, help='Available commands')

    # 'new' command
    parser_new = subparsers.add_parser('new', help='Create a new note')
    parser_new.add_argument('title', type=str, help='The title of the note')
    parser_new.add_argument('content', type=str, help='The content of the note')
    parser_new.set_defaults(func=create_note)

    # 'list' command
    parser_list = subparsers.add_parser('list', help='List all notes')
    parser_list.set_defaults(func=list_notes)

    # 'read' command
    parser_read = subparsers.add_parser('read', help='Read a specific note')
    parser_read.add_argument('title', type=str, help='The title of the note to read')
    parser_read.set_defaults(func=read_note)

    # 'delete' command
    parser_delete = subparsers.add_parser('delete', help='Delete a specific note')
    parser_delete.add_argument('title', type=str, help='The title of the note to delete')
    parser_delete.set_defaults(func=delete_note)

    args = parser.parse_args()
    args.func(args)

if __name__ == "__main__":
    main()