# Java Socket Chat Application

A multi-client chat application using Java sockets that enables public communication between connected users.

## Features

- **Unique Username**: Upon connection, each client must provide a unique username. Duplicate usernames are not allowed.
- **Message History**: Clients can see the last 100 messages, including their author and timestamp.
- **Real-Time Updates**: All clients are notified when a new message is sent or a new client connects.
- **Message Censorship**: Certain predefined words are censored by replacing the characters with asterisks (e.g., `hello` -> `h***o`).
- **Error Handling**: If an error occurs on the server or client, the connection is terminated, and an error message is printed in the console.

## How It Works

1. **Server**: 
   - Accepts client connections.
   - Ensures that each client has a unique username.
   - Maintains a message history (up to 100 messages).
   - Notifies all clients when a new message is sent or when a client connects.
   - Censors predefined words in messages.

2. **Client**: 
   - Connects to the server and provides a unique username.
   - Receives a welcome message upon successful connection and sees the chat history.
   - Sends messages to the server, which then broadcasts them to all other clients.
   - Displays new messages in the format:
     ```
     <Date and Time> - <Username>: <Message>
     ```

## Message Censorship

Messages containing censored words will be modified by replacing all characters except the first and last with an asterisk (`*`). For example, the word "badword" would be censored as `b*****d`.
